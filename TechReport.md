A study of security rules for Android
=====================================


Abstract
---------

Android is one of the most popular mobile operating systems today. A large number of apps in a plethora of domains are being developed for it everyday. A number of these apps deal with sensitive information which if compromised can have dire consequences for the user. Therefore, developers have an added responsibility to develop apps that are secure. In this paper we study a bunch of security rules and find out how they can be enforced without hampering the current approach that developers follow to build apps on Android.


Introduction
-------------

The CERT Division of the Software Engineering Institute (SEI) at Carnegie Mellon University has for a variety of platforms developed a set of secure coding standards[1]. Some of such standards are rules and some are guidelines; a standard is classified as a rule if a violation of it always must constitute a defect in the code, whereas guidelines cannot be automatically enforced as they may be violated at the discretion of the programmer. For each rule, we aim at detecting if it can be incorporated into the Android platform to aid secure development without hampering the user experience or the ease of development. On the other hand, a guideline will most likely not be feasible for enforcing into the Android system because that could make the system more restrictive; still developers may or may not decide to enforce it at app level. Each of the following sections desribe a rule and how it can be enforced.
 






References
-----------

[1] https://www.securecoding.cert.org/confluence/display/android/Android+Secure+Coding+Standard. 