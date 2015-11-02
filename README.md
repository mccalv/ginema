# Ginema  
The central ideal behind "Ginema" is to *decouple the sensitive data from the domain model classes* in order to perform federated search against different datasets and cloud storage. 
Sensitive date are stored in separated hash based structures and serialized using several strategies transparent to the domain model.


```java
@SensitiveDataRoot(name = "aSimpleObjec")
public class SimpleDomainObject {

  private SensitiveDataID id;
  private SensitiveDataField<String> name;
  private SensitiveDataField<Date> dateOfBirth;
  private SimpleDomainObject child;
```




 

