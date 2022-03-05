# SmortCAD2.0
## Stream lining emergency response

**Key functionalities:**
- Automatically categorizing information appropriately entered into a text box
- Emulate first responders on a map with blips/images
- Randomized 911 calls


This application will serve as a proof-of-concept training simulator for new emergency dispatchers
such that they can gain hands on experience in implementing calls for service to emulated first responders.
The application will list new calls in order of priority, allow emulated responders to be attached to the call
or accept low-priority calls etc. It will behave in a sense like a game.

This application will be designed such that any new emergency call-taker can train in a low-stress environment.

This is of interest to me because I am curious about first-response.

## User Stories

- As a user, I want to be able to see the status of any call
- As a user, I want to be able to create a call, enter the call details and close it appropriately.
- As a user, I want to be able to login with a username and password to access the main menu.
- As a user, I want to be able to create a new responder and add them to a master roster of all responders. 
- As a user, I want to be able to assign a responder to call.
- As a user, I want to be able to create calls and add them to a master roster of all calls, being able to distinguish between in-progress and completed calls.
- As a user, I want to be able to track call statistics by saving all the calls and responders from the previous shift
- As a user, I want to be able to load the previous shift's call history and responders from file.


## Phase 4: Task 2

- Tue Nov 23 08:26:54 PST 2021
- New responder on-duty, number: 2, Ambulance
- Tue Nov 23 08:27:11 PST 2021
- Call added, number: 0
- Tue Nov 23 08:27:18 PST 2021
- New responder on-duty, number: 23, Fire
- Tue Nov 23 08:27:25 PST 2021
- New responder on-duty, number: 29, Police
- Tue Nov 23 08:28:03 PST 2021
- Call added, number: 1
- Tue Nov 23 08:28:11 PST 2021
- Assigned responder: 29 to call at: 2068 Main Mall
- Tue Nov 23 08:28:21 PST 2021
- Assigned responder: 23 to call at: 2075 West Mall
- Tue Nov 23 08:28:26 PST 2021
 - Assigned responder: 2 to call at: 2075 West Mall
 - Tue Nov 23 08:28:36 PST 2021
 - Call completed, number: 0
 - Tue Nov 23 08:28:36 PST 2021
 - Returned: 2, Ambulance back to service
 - Tue Nov 23 08:28:36 PST 2021
 - Returned: 23, Fire back to service
 - Tue Nov 23 08:28:36 PST 2021
 - Returned: 29, Police back to service
 - Tue Nov 23 08:28:55 PST 2021
 - Call added, number: 2
 - Tue Nov 23 08:29:00 PST 2021
 - Call completed, number: 1
 - Tue Nov 23 08:29:12 PST 2021
 - Assigned responder: 29 to call at: 2068 Main Mall
 - Tue Nov 23 08:29:17 PST 2021
 - Call completed, number: 2
 - Tue Nov 23 08:29:17 PST 2021
 - Returned: 29, Police back to service



## Phase 4: Task 3

- Abstract the common functionalities in AllCallsForService and AllResponders into one abstract
class and have both AllCallsForService/Responders extend it and override the functionalities as required.

- Might be possible to apply the composite pattern, have CallForService as leaf, Responder as composite and AllX as component
  - The shared functionality abstracted in the component could be creating lists of numbers and some getters/setters

- Might add some low coupling by creating instances of the model classes in CADUI to decrease the amount of class-level fields so I can use getters/setters to set the text boxes.
  - ie: private AllResponders allresponders; etc.

- Could introduce new classes to refactor some of the JLabel/ Text functionalities to decrease the amount of class-level fields in CADUI.