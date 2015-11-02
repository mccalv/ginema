# Ginema  
The central ideal behind "Ginema" is to *decouple the sensitive data from the domain model classes* in order to perform federated search against different datasets and cloud resources. 
Sensitive date are stored in separated hash based structures and serialized using specific strategies transparent to the domain model.
## Getting started:
You just need to annotate your domain model this way:

```java
@SensitiveDataRoot(name = "aSimpleObjec")
public class SimpleDomainObject {

  private SensitiveDataID id;
  private SensitiveDataField<String> name;
  private SensitiveDataField<Date> dateOfBirth;
  private SimpleDomainObject child;
```
The framework supports the process of:
* Serialization: 
* Deserialization

The simplest enrichment supported is the one to convert an object and extract its sensitive data fields:
```java
  SensitiveDataHolder enrich = SensitiveDataEnricher.enrich(s);
```
The SensitiveDataHolder is an Apache Avro structure language agnostic which can be serialized in any possible enviroment
[Apache Avro!](https://avro.apache.org/)





 

