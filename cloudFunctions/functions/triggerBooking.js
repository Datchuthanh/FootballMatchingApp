const functions = require('firebase-functions');

const admin = require('firebase-admin');
const db = admin.firestore();
const auth = admin.auth();

// gửi thông báo đến sân khi có team đặt , đã xử lý xong 
exports.triggerBooking = functions.firestore.document('Booking/{requestId}').onCreate(async (snap, context) => {
    const newValue = snap.data();
    console.log(context.params.requestId);
    // get team
    const idField = newValue.idField;
    const fieldRecord = await db.collection('Field').doc(idField).get();
    const fieldData = fieldRecord.data();
    // get player
    const idTeam = newValue.idTeamHome;
    const teamRecord = await db.collection('Team').doc(idTeam).get();
    const teamData = teamRecord.data();
    // get captain


    const message = {
        data: {
            messageType: "NewBooking",
            idTeam: idTeam,
            nameTeam: teamData.name
        },
        token: fieldData.registrationToken
    }
    admin.messaging().send(message).then((response) => {
        console.log('Successfully sent message: ', response);
        return;
    }).catch((error) => {
        console.log('Error sending message: ', error);
        return;
    })
});



// 2 thứ : 1 là thông báo sân đồng ý cho đặt , và thông báo cho các thành viên của cả 2 team khi team sân nhà accept team chờ phê duyệt 
exports.triggerAcceptBooking = functions.firestore.document('Booking/{recordId}').onUpdate(async (change, context) => {
    //vế 1
    const bookingAfter = change.after.data();
    const bookingBefore = change.before.data();
    let i;
    const fieldRecord = await db.collection("Field").doc(bookingAfter.idField).get();


    const teamHomeRecord = await db.collection("Team").doc(bookingAfter.idTeamHome).get();
    const listPlayerRecordID = await db.collection("Team").doc(bookingAfter.idTeamHome).collection("listPlayer").get();
    
    let listPlayerPromises = [];
    let teamMemberRegistrationTokens = [];
   
    if (!listPlayerRecordID.empty) {
        for (i = 0; i < listPlayerRecordID.docs.length; i++) {
            listPlayerPromises.push(db.collection("Player").doc(listPlayerRecordID.docs[i].data().player).get())
        }
    }

    await Promise.all(listPlayerPromises).then((playerRecords) => {
        for( i = 0 ; i< playerRecords.length ; i++){
            teamMemberRegistrationTokens.push(playerRecords[i].data().registrationToken);
         }
         return null;

    })

    let messageToOthers;
    //send Accept Booking
    if (bookingAfter.approve === true && bookingBefore.approve === null) {
        messageToOthers = {
            data: {
                messageType: 'Accept Booking',
                teamName: teamHomeRecord.data().name,
                fieldName: fieldRecord.data().name,
                startTime: bookingBefore.startTime,
                endTime: bookingBefore.endTime,
                dateTime : ""+bookingBefore.date, 
            },
            tokens: teamMemberRegistrationTokens
        }
    }

 
     if (bookingAfter.approve === true && bookingAfter.idTeamAway !== null) {
      
        const teamAway = await db.collection("Team").doc(bookingAfter.idTeamAway).get();
        const playersAway = await db.collection("Team").doc(bookingAfter.idTeamAway).collection("listPlayer").get();
     
         listPlayerPromises = [];
      

    if (!playersAway.empty) {
        for (i = 0; i < playersAway.docs.length; i++) {
            listPlayerPromises.push(db.collection("Player").doc(playersAway.docs[i].data().player).get())
        }
    }

    await Promise.all(listPlayerPromises).then((playerRecords) => {
        for( i = 0 ; i< playerRecords.length ; i++){
            teamMemberRegistrationTokens.push(playerRecords[i].data().registrationToken);
         }
         return null;

    })

        messageToOthers = {
            data: {
                messageType: 'Accept Matching',
                teamHomeName: teamHomeRecord.data().name,
                teamAwayName: teamAway.data().name,
                fieldName: fieldRecord.data().name,
                startTime:  bookingBefore.startTime,
                endTime:  bookingBefore.endTime,
                dateTime : ""+bookingBefore.date, 
            },
            tokens: teamMemberRegistrationTokens
        }

        
    
    }

    admin.messaging().sendMulticast(messageToOthers).then((response) => {
        console.log('Successfully sent message: ', response);
        return;
    }).catch((error) => {
        console.log('Error sending message: ', error);
        return;
    })

});  
