# Pet Screening challange

Pet screening coding challange 
## Features

-  petById(p_id: String): Pet 
- searchPets(criteria: PetSearchCriteria): [Pet]
- checkPetEligibility(petId: String!): PetEligibility
- addPet
- ...

## Getting Started

### Prerequisites

- Docker
- Docker compose
- Java
- GraphQL

### Installation

run "docker-compose up -d"


### Running the Application

run main [PetscreeningApplication.java](src%2Fmain%2Fjava%2Fcom%2Fexample%2Fdemo%2FPetscreeningApplication.java)
and  open
http://localhost:8080/graphiql?path=/graphql
## Usage

Sample GraphQL query:

```graphql
query findPet {
  petById(p_id: "pet-2akIHnzZrRtvzY485j5QPK1nBdt") {
    id
    name
    breed
    weight
    trainingLevel
    owner {
      name
      email
      pId
    }
  }
}

query checkPetEligibility {
  checkPetEligibility(petId: "pet-2agYPWx9iCrBlu4j8SNsyjkdvL8") {
    petId
    isValidPet
  }
}

mutation createPet {
  addPet(
   petInput: {name: "MewStrong"
    weight: 42
    vaccinated: true
    breed: "FRENCH_BULLDOG"
    trainingLevel: 3
    ownerInput: {  name: "Juan"
    email: "jm@hotmail.com"
    address: "ibarblaz 1330 Cordoba"
    phone: "+543513567182" }
  }) {
    name
    pId
    breed
    trainingLevel
    vaccinationStatus
  }
}

query search {
  searchPets(criteria: {weight: 15}) {
    id
    name
    birthdate
    weight
    breed
    owner {
      id
      name
      email
    }
    trainingLevel
    vaccinationStatus
  }
}


