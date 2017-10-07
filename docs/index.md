# ByteMechanics
Website version: 0.0.1
ByteMechanics try to become a place to share with the community some utility java libraries that follow my "Seven Rules for Developer"
(Note: This website is under development, as soon as we have time we'll include more information)

## Seven Rules for Developer
### 1. Avoid "The magic"
#### Illness
Most times developers use libraries without understanding fully what's happening under the hood.
#### Effects
* Developing in try a error basis because detects that the application behavior is not as pretended. 
* Code duplication due not found current classes
* Application increase of CPU and Memory consumption
#### Solution
Never use any library that you don't understand how it works inside. It's not necessary to know how it works only to know in which technologies and which resources use.
#### Examples
* ORM case: if you do not understand that every EAGER relation will generate a full select of all the children probably you'll end loading full database in memory
* Inversion of control: Dependency injection can be a powerful tool to avoid unnecessary boilerplate, but also can be harmful for novice developers, as they do not understand how it works internally:
** How much instances exist
** If you have the required context to allow injection.
### 2. Limit dependencies
### 3. Runaway from consultant style
### 4. Be straightforward 
### 5. Choose stateless over stateful
### 6. Don't be naive
### 7. One rule to rule them all: there is no absolute rule

## Libraries
All libraries require at least JDK8
#Type-ex
Type-ex it's a library to make easy application errors classification. 
In the old times all application errors were indexed in never-ending lists in big manuals to describe the possible problems and its solutions. 