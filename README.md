[ ![Download](https://api.bintray.com/packages/arapoport/main/cuba-pswdplus/images/download.svg?version=1.0.3) ](https://bintray.com/arapoport/main/cuba-pswdplus/_latestVersion)
[![license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)


## What is this component for ? 
**cuba-pswdplus** is a CUBA Platform component that provides "out of the box" password history validation to an existing application. It allows the app administrator to activate and configure the validation.  

A coming version will add support for password expiration and thus, it will allow a CUBA application to be deployed in corporate environments with strict password requirements.


## Installation
1. `cuba-pswdplus` is available in the [CUBA marketplace](https://www.cuba-platform.com/marketplace)
2. The component was tested only in platform version 6.10.x

The latest version is:[ ![Download](https://api.bintray.com/packages/arapoport/main/cuba-pswdplus/images/download.svg?version=1.0.3) ](https://bintray.com/arapoport/main/cuba-pswdplus/_latestVersion)

To install the component, you have to add the repository and the component in CUBA Studio or in a `build.gradle` file. The complete add-ons installation guide is explained in [CUBA Platform documentation](https://doc.cuba-platform.com/manual-latest/app_components_usage.html)

Add a custom application component to your project:

* Artifact group: `ar.com.osmosys.pswdplus`
* Artifact name: `pswdplus-global`
* Version: *add-on version*

## Using the component

Once you've installed the component and run your app for the first time, open the Application Properties screen in the Administrator menu. You will see a new entry named **pswdplus**:

![Screenshot Application Properties](https://github.com/pakuda/pswdplus/blob/feature/readme/img/appProperties.png)

