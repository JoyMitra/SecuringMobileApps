A study of security rules for Android
=====================================


Abstract
---------

Android is one of the most popular mobile operating systems today. A large number of apps in a plethora of domains are being developed for it everyday. A number of these apps deal with sensitive information which if compromised can have dire consequences for the user. Therefore, developers have an added responsibility to develop apps that are secure. In this paper we study a bunch of security rules and find out how they can be enforced without hampering the current approach that developers follow to build apps on Android.


1. Introduction
----------------

The CERT Division of the Software Engineering Institute (SEI) at Carnegie Mellon University has for a variety of platforms developed a set of secure coding standards[1]. Some of such standards are rules and some are guidelines; a standard is classified as a rule if a violation of it always must constitute a defect in the code, whereas guidelines cannot be automatically enforced as they may be violated at the discretion of the programmer. For each rule, we aim at detecting if it can be incorporated into the Android platform to aid secure development without hampering the user experience or the ease of development. On the other hand, a guideline will most likely not be feasible for enforcing into the Android system because that could make the system more restrictive; still developers may or may not decide to enforce it at app level. 

Section 2 gives a brief introduction of the Android security model. Section 3 and the following sections describe each of the security rules we mentioned above along with how they can be enforced.


2. Android Security Model
--------------------------

Android is a privilege separated operating system where every application runs with a unique system ID, called the UID, in its own sandbox. A central design point of the Android security architecture is that no application has permission to adversely impact other applications, the operating system or the user. Every application in Android has four high level components namely, Activity, Service, Broadcast Receiver and Content Provider. Each component can interact with each other with the help of something called Intent. Every component inside an application can call each other by just passing intents and does not need any permission to do so. A component can access other components outside its own application sandbox if the Android system grants it permission. 

Permissions can be either defined by the Android system or can be defined by app developer. Each of the system defined permissions is used to protect some resource on the phone. Typical examples of system defined permissions are CALL_PHONE used to make phone calls, INTERNET used to open network sockets, SEND_SMS used to send messages, CAMERA used to capture images etc. Custom permissions defined by the app developer are typically used to protect components created by the developer. Each permission in android has a name, label, description, permission group and protection level. The name must be unique and is used in code to refer to the permission. Permissions with conflicting names cannot exist in the Android system. Label and description are used to describe the role of the permission to the user. Permission group is used to specify the category of the permission like camera, Calendar, contacts, location etc. This allows the user to grant a set of permissions in a single action. Finally and most importantly protection level specifies the procedure the Android system should take to grant permission to the requesting application. Android permissions have four protection levels namely-- normal which is granted by the system automatically at install time, dangerous which is granted by the system at install time only if the user approves, signature which is granted only if the application requesting it is signed with the same key as the one used to sign the application that declared the permission and finally signatureOrSystem which is the same as signature but is only granted to apps in the Android system image. The default level is normal. An application must request upfront all the permissions it needs. When an application is installed, the package manager goes through all the permissions requested by the app. Normal permissions are granted by default by the system, signature permissions are granted by the system if and only if the app requesting the permission has the same signature as the app that defined the permission, dangerous permissions are granted only if the user approves it. If a user denies even a single dangerous permission then the app is not installed. An app is also not installed if it declares a permission that is already defined in the system.

#### 2.1 Enforcing Permission

Once an app has been installed in the system, it has been granted all the permissions it had requested at install time. When a particular component is called, the android system checks if the uids of the caller and the callee match. If they match access is instantly granted. If they do not match then it checks if the callee component is exported or public. If it is not exported then the call fails. However, if exported then it checks whether it is protected by a permission. If it is protected by a permission then it checks if the caller component has been granted that permission by referring to the permission name attribute. If permission has been granted then the caller is allowed to call the callee else a security exception is thrown. However, if the exported callee component is not protected by a permission then the caller is granted access anyway.  

#### 2.2 Android 6.0 -- A new permission mechanism

Versions prior to Android 6.0 granted all permission at install time. The downside of this approach was that user’s weren’t allowed to install the app if they disagreed with even one permission. For instance, if a user wanted to install a gaming app and that app wanted access to the internet but the user was not comfortable with it, then the user would have to deny installation. With android’s new permission mechanism users do not have to make that choice at install time. Permissions with protection level dangerous in Android’s new model are not granted when the app is installed. Permissions with protection level normal and protection level signature are still granted at install time though. When the app runs and one of its component wants to access another component protected by a permission whose protection level is dangerous then the user is prompted for approval. If the user approves then access is granted. If the user denies then access is not granted and the app developer should handle the access denial gracefully. If the user denies permission but tries to access the functionality that requires this permission again then the user is prompted again for approval but this time with a “do not ask again” option. If the user approves then access is granted. But if the user denies and selects “do not ask again” then user will never be prompted again and the app will never be granted this permission. Also, users have the option of revoking permissions. For instance, if a user wants to upload an image to the Facebook app then Facebook will request the user to grant access to gallery. The user grants access but can revoke it after uploading the app. Therefore, developers are advised to always check whether their app has the required permission before accessing a resource and handle it gracefully if permission is not granted otherwise their apps might crash.


3 Component Security Rule
--------------------------

A key ingredient in preventing unauthorized access is expressed by the Android CERT rule[1].
	*DRD14-J: Check that a calling app has appropriate permissions before responding.
	
But the adequacy of this depends on the meaning of *appropriate*. Android's current security model does not necessarily prevent a component from unauthorized access from a component
outside its sandbox. This is for two reasons, one a design flaw and the other more an implementation flaw.

		1.	Android's model does not check the identity of a caller component, but implicitly assumes that since it has been granted the permission, its access request is legitimate.
			Hence a malicious app can use those permissions in its own manifest to gain access to a protected component even though the component was not intended to be accessed by the malicious app. 
			
		2.	Android's model does not check if the permission may have been granted with another protection level. Hence, if a protective permission is re-declared as *normal* by a 
			malicious app (and up until Android5.0 it was possible to upgrade or downgrade protection levels), a colluding app could then use this permission in its manifest; even if the malicious app is later uninstalled, when the benign app with the sensitive component is installed, the colluding app will still be able to use that permission to gain access.

We propose to solve both problems by allowing the developers of a component to specify, in a *white list*, the identities of the apps allowed to access it, and by checking at run-time not just that the caller has the required permissions but also that the protection levels are the same as when originally granted. This approach could potentially solve the problem of unauthorized access without hampering the user experience as could happen when permissions suddenly get revoked (which is what the most recent Android system does to prevent dangling permissions[2]).	

#### 3.1 Experimental Results

We created an emergency app to send SMSes to five people chosen by the user from his/her contactlist. To funtion correctly, this app needs the following permissions: SEND_SMS, READ_CONTACTS and ACCESS_FINE_LOCATION. The code to send SMSes is written in a service of the app. We then used the emergency app to create two attack scenarios:

		1.	We protected the service used to send SMSes with a custom permission declared as *normal". We installed this app on a device. We then created a malicious app that requests 
			at install time the custom permission protecting the service. We then installed the malicious app ontothe device. We successfully called the service to send SMSes even though the malicious app did not have permissions to send SMSes. The Android system assumed that because the malicious app has been granted permission to access the service, it must be a legitimate access.	
			
		2.	In another scenario, we protected the service with a custom permission declared as *signature* (since it deals with functionalities that could potentially harm the user if 
			misused). We then created a malicious app A and in its manifest declared the same custom permission protecting the service with protection level \normal". We created another malicious app B that uses the declared permission in its manifest and calls the service in the emergency app. We installed malicious apps A and B successfully onto the device. We then uninstalled malicious app A and installed our emergency app. We ran malicious app B to successfully send an SMS to one of the user's contacts using the service in the benign app. Malicious app B did not have SEND SMS permission and yet it was able to escalate its privilege by using the permission granted to the service in the emergency app.
			

#### 3.2 Proposed Solution

The problem of downgrading protection levels has been explored before[2, 3, 4]. In Android 5.1, the problem was resolved by revoking the permission whose definition does not exist. This prevents our second attack scenario, since the colluding apps permission will be revoked when the malicious app is uninstalled. On the other hand, this approach introduces a new problem where legitimate apps could crash due to the sudden revocation of a permission which had earlier been granted to it by the system. Moreover, the approach does not solve the problem of apps being granted permissions not intended by the developer. We shall propose an alternative solution: for each component, maintain a white list of those apps, and their signatures, that are allowed to interact with it. When a call is made to this component from outside its sandbox, then we check if the permission granted to the caller matches
the permission name and protection level of the permission protecting the callee. If this test is passed, we check if the protection level is *signature*: if it is then access is granted provided that the caller has the same signature as the callee; if not then we check the white list and grant access iff the caller is present there. While this approach does not solve the problem of dangling permissions, it does prevent it from causing unauthorized access, and does so without affecting the availability of the app. As an extra benefit, the white list mechanism will enable components to explicitly specify who is authorized to access it.


We implemented this algorithm at the app level and managed to prevent unauthorized access to the service. We also implemented this in Android 4.4 and successfully managed to prevent the malicious app from accessing the messaging component in the benign app, in spite of having permissions to do so. Please see our git hub repo for patches and details of how to reporoduce the exploit[5]. 






References
-----------

[1] https://www.securecoding.cert.org/confluence/display/android/Android+Secure+Coding+Standard. 

[2] H. Bagheri, E. Kang, S. Malek, and D. Jackson. Detection of design flaws in the Android permission protocol through bounded verification. In Formal Methods, 20th International
Symposium (FM 2015), volume 9109 of Lecture Notes in Computer Science, pages 73-89.Springer-Verlag, 2015.

[3] D. Chell, T. Erasmus, S. Colley, and O. Whitehouse. The Mobile Application Hacker's Hand-book. Wiley, Feb. 2015.

[4] W. Shin, S. Kwak, S. Kiyomoto, K. Fukushima, and T. Tanaka. A small but non-negligible flaw in the Android permission scheme. In Proceedings of the 2010 IEEE International Symposium on Policies for Distributed Systems and Networks, POLICY '10, pages 107{110, Washington, DC, USA, 2010. IEEE Computer Society.

[5] https://github.com/santoslab/SeMA/tree/master/ComponentSecurity

[6] https://source.android.com/security/overview/app-security.html