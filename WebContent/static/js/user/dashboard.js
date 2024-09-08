const recentActivities = [
    // 'Checked into room 101',
    // 'Requested room service',
    // 'Checked out and paid bill'
];
const upcomingReservations = [
    // 'Reservation ID: 1234, Check-in: 2024-08-05, Check-out: 2024-08-10',
    // 'Reservation ID: 5678, Check-in: 2024-09-01, Check-out: 2024-09-05'
];
const notifications = [
    // 'Your recent payment was successful.',
    // 'Your upcoming reservation is confirmed.'
];


document.getElementById('username').textContent = localStorage.getItem("loginUser");


const recentActivitiesList = document.getElementById('recent-activities');
recentActivities.forEach(activity => {
    const listItem = document.createElement('li');
    listItem.textContent = activity;
    recentActivitiesList.appendChild(listItem);
});


const upcomingReservationsList = document.getElementById('upcoming-reservations');
upcomingReservations.forEach(reservation => {
    const listItem = document.createElement('li');
    listItem.textContent = reservation;
    upcomingReservationsList.appendChild(listItem);
});


const notificationsList = document.getElementById('notifications');
notifications.forEach(notification => {
    const listItem = document.createElement('li');
    listItem.textContent = notification;
    notificationsList.appendChild(listItem);
});
