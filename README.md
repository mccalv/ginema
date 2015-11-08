# Ginema  
<a href="https://travis-ci.org/mccalv/ginema">
<img title="Build Status Images" src="https://travis-ci.org/mccalv/ginema.svg">
</a>

The central ideal behind "Ginema" is to *decouple the sensitive data from the domain model classes* in order to perform federated search against different datasets and cloud resources. 
Sensitive date are stored in separated hash based structures and serialized using specific strategies transparent to the domain model.
## Getting started:
You just need to annotate your domain model using the following apparoach:

```java
@SensitiveDataRoot(name = "simpleDomainObject")
public class SimpleDomainObject {

  private SensitiveDataID id;
  private SensitiveDataField<String> name;
  private SensitiveDataField<Date> dateOfBirth;
  private SimpleDomainObject child;
```
The framework supports the process of:
* Serialization: 
* Deserialization

The simplest enrichment supported is the one to extract from an object its sensitive data fields:
```java
  SensitiveDataHolder enrich =  SensitiveDataExtractor.extractSensitiveData(object);
```
The opposite operation is to populate an object with sensitive data

```java
  SensitiveDataEnricher.enricher(sensitiveDataHolder,object);
```

The SensitiveDataHolder is an Apache Avro structure language agnostic which can be serialized in any possible enviroment
[Apache Avro!](https://avro.apache.org/).



## Requirement:
Java 1.8 
Maven 3.1.1



 

