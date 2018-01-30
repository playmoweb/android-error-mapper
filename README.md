ErrorMapper allows you to annotate methods with @ShowError or @ShowErrors to bind and call automatically these methods with a kvp HashMap.

This library is recommended to be used in form when errors are formatted to a specific and common structure. It uses reflection behind the hood.


# Usage example

```java
public class YourActivity {

    @ShowError("firstname")
    public void yourMethod1(String key, String error){
        //
    }

    @ShowError("address")
    public void yourMethod2(String key, String error){
        //
    }

    @ShowErrors({"firstname", "lastname", "age"})
    public void yourMethod3(String key, String error){
        if(key.equals("firstname")){
            // special case
        } else {
            // other cases
        }
    }
}
```

```java
public class YourPresenter {
    private final ErrorEntityMapper errorMapper;

    public YourPresenter(YourActivity view){
        errorMapper = ErrorEntityMapper.build(view);
    }

    public void doSomething(){
        /*
        // From a json deserialized response like this :

        {
            "firstname": "Invalid first name",
            "lastname:" "Should not contains numbers",
            "address": "This city does not exist"
        }
        */

        // depending on your payload structure, here we use a HashMap<String, String> but
        // you can use any type of objects, like : HashMap<String, MyCustomObject>
        // and get it back in the callback !

        HashMap<String, String> myHashMap = /* json.deserialize() */;
        errorMapper.with(myHashMap); // will call all methods if errors are found !
    }
}
```