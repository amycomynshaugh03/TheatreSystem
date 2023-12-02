package models

data class Event( var eventId: Int = 0,
                  var eventTitle: String,
                  var eventCategory: String,
                  var eventDescription: String,
                  var ageRating: Int = 0,
                  var ticketPrice: Int = 0,
                  var eventDuration: Int = 0
)