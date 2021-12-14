

# Wyrd Choices!
An Android app dedicated to CYOA (Create Your Own Adventure). A tool to make and play text-based games.	:pinched_fingers:


## Creating an adventure?
- Provide input for all fields. Fields that are greyed out aren't required.
- Customization for buttons are available within cardviews. The field labeled ***Leads to scene*** requires a number corresponding to the scenario. 
  > The first scenaraio created is scenario 0, the next one after that is scenario 1, and so on. Foresight is highly recommended.
- The "End" radio button corresponds to a scenario without any buttons. All scenarios are set to normal by default.

## Registration 
- The app does not require real email addresses, nor does it send any form of email, notification and whatnot to potentially valid ones.
- Registration isn't required. Login faster using *"admin"* as both password and username


## Issues
- [ ] Non-functioning icons in bottom app bar in the *Adventure Select* screen :unamused:
- [ ] No recommender tool for checking valid button references during *Adventure Create* :expressionless:	
- [ ] Buttons referencing inexistent scenarios aren't checked. :nauseated_face:
- [ ] User-created adventures are saved locally but not remotely in database. 	:sleepy:
 
## Debugging
- If clicking a particular item in the RecyclerView makes the app crash, call deleteFile() in LoginActivity.java. This deletes locally-saved, app-specific files.
  > Change the file name indicated in the method to *[adventure title].txt*


## References
- View components from [Material Design](https://material.io/).
- Database built using [Firebase](https://firebase.google.com/)
