# Getting started

* [System Requirements](#system-requirements)

* [Adding Cards](#adding-cards-widgets)
  1. Import dependency
  2. Register SDK into your Application
  3. Authorization
  4. Initializing Widgets
     * Card
     * Bottom Sheet Card Information
     * Activate Card
     * Change PIN
     * On Result Listener
* [Styling](#styling)
   * [Icons and images customization](#icons-and-images-customization)
* [Release Notes](#release-notes)

&nbsp;
&nbsp;
---
# System Requirements

**Minimum SDK version:** 21 (Android 5.0)

**Compile SDK version:** 33

---
# Adding Cards Widgets
## 1. Import dependency
Pomelo Card Widgets is hosted in GitHub Packages. For more information refeer to their [site](https://docs.github.com/es/packages/learn-github-packages/installing-a-package)

*settings.gradle*

``` groovy
maven {  
    name = "Pomelo GitHubPackages Releases"  
  url = uri("https://maven.pkg.github.com/pomelo-la/android-public")  
    credentials {  
        username = "YOUR_USER_NAME" 
        password = "YOUR_ACCESS_TOKEN"
    }  
}
```
*build.gradle*

``` groovy
dependencies {
    com.pomelo:cards-widgets:1.0.0
}
```
Refer to *Release notes* for the latest version

&nbsp;

---
## 2. Register Cards Widgets SDK into your Application

Inside your Application's `onCreate` function call `PomeloCards`.register() passing the Application reference
``` kotlin
class YourApplication: Application() {  

   override fun onCreate() {  
     super.onCreate()
     
     PomeloCards.register(
            configuration = Configuration {
                repository.getUserToken(UserTokenBody("user_email@domain.com))
            },
            context = this
        ) 
}  
```
---
## 3. Authorization

---
## 4.  Initializing Widgets
## Card
<img src="https://user-images.githubusercontent.com/9848247/206248330-032a478e-070e-4a2d-a0a3-fe661629be06.png"/><img src="https://user-images.githubusercontent.com/9848247/206260248-6773169e-ac56-4751-9a2d-1c5467b53e80.png"/>

Card image with sensitive data.

### Compose
```kotlin
    var showSensitiveCardData by rememberSaveable { mutableStateOf(false) }
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    PomeloCardView(
        name = "Juan Perez",
        lastFourDigits = "1234",
        showCardData = showSensitiveCardData,
        cardId = "crd-ABCD1234",
        imageUrl = "https://url",
        scaffoldState = scaffoldState,
        onResultListener = { result, _ ->
            when (result) {
                NETWORK_ERROR -> {}
                BIOMETRIC_ERROR -> {}
                SUCCESS -> {}
            }
        }
    )
    
    OutlinedButton(
        onClick = { showSensitiveCardData = !showSensitiveCardData }
    ) {
        Text(
            text = "Ver datos",
            style = MaterialTheme.typography.button
        )
    }
```

### XML
```kotlin
class PomeloCardView 
```

#### Summary
##### Public functions

###### init
```kotlin
 fun init(name: String, lastFourDigits: String, imageUrl: String)
```
Initialize the widget with its content.

| Parameters   | |  
| ------------ | ------------ |
| **name**: String  | Name of the Card holder. It will be shown as upper case.  |
| **lastFourDigits**: String  | Last four digits of the PAN.  |
| **imageUrl**: String  | Url of the card image. The aspect ratio must be of **1.586**. |

---
###### showSensitiveData
```kotlin
 fun showSensitiveData(cardId: String, onResultListener: OnResultListener?, activity: AppCompatActivity)
```
Reveals the hidden sensitive data from the card after passing successfully a biometric prompt.

| Parameters   | |  
| ------------ | ------------ |
| **cardId**: String  |  example _crd-ABCD1234_e |
| **onResultListener**: [OnResultListener](#onresultlistener) |  |
| **activity**: AppCompatActivity  | Current Activity. |

---
## Bottom Sheet Card Information
<img src="https://user-images.githubusercontent.com/9848247/206261361-bdb05969-9adb-4c00-ae41-7eb450b285fe.png"/>

Bottom sheet with sensitive data from the card.

### Compose
```kotlin
  var showCardBottomSheet by remember { mutableStateOf(false) }

  OutlinedButton(
        onClick = { showCardBottomSheet = true }
    ) {
        Text(
            text = "Ver bottom sheet",
        )
    }
                        
  PomeloCardBottomSheet(
      cardId = "crd-ABCD1234",
      titleCard = "Tarjeta Virtual",
      showSensitiveData = showCardBottomSheet,
      onDismiss = { showCardBottomSheet = false },
      scaffoldState = scaffoldState,
      onResultListener = { result, _ ->
              when (result) {
                  NETWORK_ERROR -> {}
                  BIOMETRIC_ERROR -> {}
                  SUCCESS -> {}
              }
          }
  )
```

### XML
```kotlin
class PomeloCardBottomSheet 
```
#### Summary
##### Public functions

###### showSensitiveData
```kotlin
 fun showSensitiveData(
        cardId: String,
        titleCard: String,
        onResultListener: OnResultListener?,
        activity: AppCompatActivity
    ) 
```
Renders a bottom sheet with sensitive data from the card after passing successfully a biometric prompt.

| Parameters   | |  
| ------------ | ------------ |
| **cardId**: String  |  example _crd-ABCD1234_e |
| **titleCard**: String  | Last four digits of the PAN.  |
| **onResultListener**: [OnResultListener](#onresultlistener)  | |
| **activity**: AppCompatActivity  | Current Activity. |

---
## Activate Card
<img src="https://user-images.githubusercontent.com/9848247/206264844-631ed776-d10d-4aff-a91a-c6e194e67db2.png"/>

Activates a new card after completing the PAN and PIN.
### Compose
```kotlin
    var activate by remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = activate,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        PomeloActivateCardView(
            scaffoldState = scaffoldState,
            onResultListener = { cardsResult, message ->
                when (cardsResult) {
                    NETWORK_ERROR -> {}
                    BIOMETRIC_ERROR -> {}
                    SUCCESS -> {
                        activate = false
                    }
                }
            }
        )
    }
```
### XML
#### Summary
##### Public functions

###### init
```kotlin
  fun init(onResultListener: OnResultListener)
```
Initialize the widget with its content.

| Parameters   | |  
| ------------ | ------------ |
| **onResultListener**: [OnResultListener](#onresultlistener)  | |

---
## Change PIN
<img width=380 src="https://user-images.githubusercontent.com/9848247/206264696-8bc3f882-058f-478f-9ade-98e5ca9abb07.png"/>

Updates the PIN number.
### Compose
``` kotlin
    var changePin by remember { mutableStateOf(true) }
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    AnimatedVisibility(
        visible = changePin,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        PomeloChangePinComposable(
            cardId = "crd-ABCD123",
            scaffoldState = scaffoldState,
            onResultListener = { result, message ->
                when (result) {
                    NETWORK_ERROR -> {}
                    BIOMETRIC_ERROR -> {}
                    SUCCESS -> {
                        changePin = false
                    }
                }
            }
        )
    }
```

### XML
#### Summary
##### Public functions

###### init
```kotlin
 fun init(onResultListener: OnResultListener, cardId: String) 
```
Initialize the widget with its content.

| Parameters   | |  
| ------------ | ------------ |
| **cardId**: String  |  example _crd-ABCD1234_e |
| **onResultListener**: [OnResultListener](#onresultlistener)  | |

---
### OnResultListener
This listener returns the result whether the user performs an action or a networking error was throw to decide what to do next on your application. For example, you would want to hide the Activate Card widget after the card was activated successfully.

The following values are the different results 
```kotlin
enum class CardsResult {
    NETWORK_ERROR,
    BIOMETRIC_ERROR,
    SUCCESS
}
```

---
# Styling
Pomelo's Card Widgets is developed to inherit your app's theme by default. It uses Material Design 2 for theming and styling but can work with AppCompat's theme with a few adjustments. 
If your app doesn't have an Application class you will need create and set in in your `Manifest.xml`
``` xml
<application android:name=".YourApplication"
             android:label="Your App Name"
             android:icon="@drawable/your_icon"
             android:theme="@style/Theme.MaterialComponents.Light.NoActionBar"> // Or your theme inheriting from Material Design
```

*We don't support Dark Mode at the moment*

### Button styling example
If any of your components doesn't look and feel as your app's theme you can customize it creating a style and setting it in your theme.

`styles.xml`
``` xml
<style name="Widget.YourAppName.Button" parent="@style/Widget.MaterialComponents.Button.UnelevatedButton">
  <item name="iconPadding">0dp</item>
  <item name="android:insetTop">0dp</item>
  <item name="android:insetBottom">0dp</item>
</style>
```
`theme.xml`
``` xml
<style name="Theme.YourAppName" parent="Theme.MaterialComponents.Light.NoActionBar">
   <item name="materialButtonStyle">@style/Widget.YourAppName.Button</item>
</style>
```
For more information on how Material Design works and how to customize views please refer to their [site](https://material.io/develop/android).

## Icons and images customization
You can replace icons and images that suits your application theme.

In your theme's xml add the following tags with your own images

```xml
<style name="Theme.YourAppName" parent="Theme.MaterialComponents.Light.NoActionBar">
  <item name="pomeloCardsContentCopyDrawable">@drawable/your_copy_icon</item>
  <item name="pomeloCardsVisibilityDrawable">@drawable/your_visibility_on_icon</item>
  <item name="pomeloCardsVisibilityOffDrawable">@drawable/your_visibility_off_icon</item>
</style>
```

<img src="https://user-images.githubusercontent.com/9848247/210417163-a23bb79b-d2ce-4c2c-a42e-b3d97413f455.png" /><img src="https://user-images.githubusercontent.com/9848247/210630733-c6408262-e6f5-437e-a050-9fe5eef390a2.png" />

---
# Release Notes
For a full list of versions refeer to this [link](https://github.com/pomelo-la/android-public/packages/1757157)
## 1.0.0
##### Date 02/JAN/2023
### Overview
### Fixes
### Improvements
