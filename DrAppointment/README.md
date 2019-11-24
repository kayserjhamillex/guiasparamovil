# Dr. Appointment

<img src="https://github.com/randyjap/DrAppointment/blob/master/app/images/Twillio.gif" width="200"> <img src="https://github.com/randyjap/DrAppointment/blob/master/app/images/Search.gif" width="200"> <img src="https://github.com/randyjap/DrAppointment/blob/master/app/images/Appointment.gif" width="200"> <img src="https://github.com/randyjap/DrAppointment/blob/master/app/images/Text.png" width="200">

Get it on Google Play Store: [Dr. Appointment][playstore]

[playstore]: https://play.google.com/store/apps/details?id=com.drappointment

## Background

Making appointments with one's primary care physician can be difficult and time-consuming. Instead of patients trying to find time during their busy lives to place phone calls and make appointments with doctors, this mobile app allows the users to quickly find their doctor, view their availabilities, and make an appointment for them or their family members anytime, anywhere.

[View Development README][readme]

[readme]: docs/README.md

Backend was built using Ruby on Rails and PostgreSQL: [DrAppointment-BE][backend]

[backend]: https://github.com/randyjap/DrAppointment-BE

## Functionality & MVP

```ruby
@client = Twilio::REST::Client.new(account_sid, auth_token)
    @client.account.messages.create({
        :to => to,
        :from => from,
        :body => message
    })
```

- Twilio API
  - Two factor authentication to verify users and their phone numbers.
  - Confirmation text when appointments are made.
  - Reminder text is sent one hour before the appointment time.

```javascript
AsyncStorage.getItem('phone_number', (err, result) => {
  const phoneNumber = result;
  if (result) {
    AsyncStorage.getItem('authy_id', (err2, result2) => {
      fetch('https://www.drappointment.io/api/session', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          user: {
            phone_number: phoneNumber,
            authy_id: result2
          }
        })
      })
      .then((response) => response.json())
      .then((response) => Actions.home({ currentUser: response }));
    });
  } else {
    return Actions.register();
  }
});
```

```javascript
this.props.authenticateUser(user)
  .then(response => {
    if (response.responseData.session_token) {
      let phone_number = response.responseData.phone_number;
      let authy_id = response.responseData.authy_id;
      AsyncStorage.setItem('phone_number', (phone_number))
        .then(() => AsyncStorage.setItem('authy_id', (authy_id)))
        .then(() => Actions.home({ currentUser: response.responseData }));
    } else {
      this.setState({
        errors: response.responseData
      });
    }
  });
```

- AsyncStorage
  - Stores user's phone number and authentication code to local storage for convenience
  - The user no longer has to go through the login process once the phone is authenticated.

```ruby
def search
  @user = current_user
  if params[:input].nil? || params[:input][:name].nil?
    @lat = params[:input][:lat]
    @lng = params[:input][:lng]
    @doctors = Doctor.all
  else
    @lat = params[:input][:lat]
    @lng = params[:input][:lng]
    @doctors = Doctor.where(
      "lower(first_name) LIKE ? OR lower(last_name) LIKE ?",
      "%#{params[:input][:name].downcase}%",
      "%#{params[:input][:name].downcase}%"
    )
  end
end
```

```ruby
json.array! @doctors do |doctor|
  name = "#{doctor.salutation} #{doctor.first_name} #{doctor.last_name}"
  json.image_url doctor.image_url
  json.id doctor.id
  json.name name
  json.address "#{doctor.street_number} #{doctor.street}"
  json.address2 "#{doctor.city}, #{doctor.state} #{doctor.zip_code}"
  json.phone "+1 #{doctor.phone_number}"
  json.favorited @user.favorite_doctors.include?(Doctor.find(doctor.id))
  json.lat doctor.lat
  json.lng doctor.lng
  json.distance (Math.sqrt((@lng - doctor.lng)**2 + (@lat - doctor.lat)**2) * 70.117663977182174).round(1)
end.sort_by! do |el|
  [el["favorited"].to_s, -el["distance"]]
end.reverse!
```

- Search
  - Search bias based on users' favorite status, location (distance to geolocation of the phone), and doctors' names.
  - User can swipe to toggle favorite or call the doctor.


```Ruby
validate :blocked?, :overlap?, :in_the_future?

private

  def blocks
    BlockedTime
      .where(doctor: self.doctor)
      .where(time_slot: self.time_slot)
  end

  def find_overlap
    Appointment
      .where.not(id: self.id)
      .where(doctor: self.doctor)
      .where(time_slot: self.time_slot)
  end

  def blocked?
    errors.add(:appointment, "is blocked by doctor") unless blocks.empty?
  end

  def overlap?
    errors.add(:appointment, "of another overlaps yours") unless find_overlap.empty?
  end

  def in_the_future?
    errors.add(:appointment, "must be in the future") if time_slot.appointment_date.appointment_date < Date.today
  end
```

- Backend Validation of appointments
  - Validation methods on the model layer prevent bad data from entering the database.
  - Errors messages are sent back to the front end for error handling


```Ruby
after_create :reminder

@@REMINDER_TIME = 1.hours

def reminder
...
Twilio::REST::Client.new(account_sid, auth_token)
    sms = @client.account.messages.create({
        :to => to,
        :from => from,
        :body => message
    })
    puts sms.to
end

def time
    date = self.time_slot.appointment_date.appointment_date
    time = self.time_slot.time
    DateTime.new(date.year, date.month, date.day, time[0..1].to_i, time[-2..-1].to_i, 0, '-8')
  end

def when_to_run
  self.time - @@REMINDER_TIME
end

handle_asynchronously :reminder, :run_at => Proc.new { |i| i.when_to_run }
```

- Asynchronous background workers handle the Twilio SMS reminder using the Delayed Job Active Record Gem.
  - The after_create lifecycle method on the model layer create a reminder event that is saved in the delayed job table and is executed when a background worker queries the table, removing it from the table when it is done
  - Adding  additional 'time' and 'when_to_run' methods allow for determining the execution interval


## Architecture and Technologies

This project will be implemented with the following technologies:

- React Native (iOS / Android)
- Rails (Heroku with SSL)
- API Authy / Twilio (text messaging)


## Future Implementations

- Create website for doctors to input their schedule.
- Implement Android version of the app.
- Upload to both the app store and the play store.
