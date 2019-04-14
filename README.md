# 1.Introduction 
**cuba-pswdplus** is a CUBA Platform component that provides "out of the box" password history validation to an existing application. It allows the app administrator to activate and configure the validation. 


# 2.Installation
To install the component, you have to add the repository and the component in CUBA Studio or in a `build.gradle` file. The complete add-ons installation guide is explained in [CUBA Platform documentation](https://doc.cuba-platform.com/manual-latest/app_components_usage.html)

## 2.1. Adding the Repository and the Component in CUBA Studio <a name="adding-cuba"></a>

1. Click *Edit* in the *Project properties* panel.

2. On the *App components* panel click the *Plus* button next to *Custom components*.

3. Paste the add-on coordinates in the coordinates field as follows: `group:name:version`. For example:

 `com.haulmont.addon.emailtemplates:yet-global:1.1.3`

 Specify the add-on version compatible with the used version of the CUBA platform.

| Platform Version  | Component Version |
|-------------------|-------------------|
| 6.10.X            | 1.0.3            |


4. Click *OK* in the dialog. Studio will try to find the add-on binaries in the repository currently selected for the project. In case they are found, the dialog will be closed and the add-on will appear in the list of custom components.

5. Click *OK* to save the project properties.




