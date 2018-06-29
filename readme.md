# Sample using Limited Input (device flow) authentication 

This sample demonstrates the ARTIK cloud services Limited Input (device flow) authentication. This flow allows a user to use a secondary device (e.g. mobile phone) to complete authentication on a device with limited input (e.g. no keyboard). In this example, a console application will authenticate when the user authenticates remotely with a phone (or another computer) via the web.

**Prerequisites**

* Java version >= 1.7
* mvn (for installing dependencies)
* Eclipse (or your favorite editor)

### Setup / Installation

You will first need to **create an application** by visiting https://developer.artik.cloud.   

1. Add any permission scope or device types to the application (optional)
2. Set the authentication type to "Limited Input" (required)
3. Retrive the `client_id` which will be needed later (required)

If you need help with any of the above steps, read the [application guide](https://developer.artik.cloud/documentation/getting-started/applications.html).

### **Code setup**

1. We will use Eclipse to import project as a maven project: `File->Import->Maven->Existing Maven Projects`
2. Edit the `/src/Config.java` and enter your `client_id` you retrieved earlier.

```java
class Config {	
	public static final String CLIENT_ID  = "your client id";
    //...
}    
```

###Run sample  

Run the `DeviceFlowLimitedInput.java`. Here are the steps using Eclipse IDE: 

```
Right Click `DeviceFlowLimitedInput.java` from the editor
Run As -> Java Application
```

If running correctly, it will display a code for the user to enter at the link `https://artik.cloud/go`, and will wait until user has completed this step.

```java
Generating code ...
> status 200
> Go to https://artik.cloud/go and enter code GCBH-DKZP
```

Using the browser (or any secondary device, e.g. mobile phone), go the the above link to login, enter code, and grant any permissions requested. Upon completion of entering the code, the running script earlier will acknowledge success.

```java
//...
Waiting to enter code, checking again in 5 secs ...
Waiting to enter code, checking again in 5 secs ...

Successful!  Code has been entered and received access token: 
abcdef0123456789c8192990aed8a57...

You may revoke your token by visiting https://my.artik.cloud/profile
or by using https://accounts.artik.cloud/revokeToken API call
```



License and Copyright
---------------------

Licensed under the Apache License. See [LICENSE](LICENSE).

Copyright (c) 2018 Samsung Electronics Co., Ltd.
