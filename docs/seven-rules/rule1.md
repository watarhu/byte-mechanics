# Rule 1: Avoid "The magic"

## Illness
Most times developers use libraries without understanding fully what's happening under the hood.

## Effects
* Developing in try a error basis because detects that the application behavior is not as pretended. 
* Code duplication due not found current classes
* Application increase of CPU and Memory consumption

## Solution
Never use any library that you don't comprehend how it works inside. It's not necessary to be one of it's developers to understand how it's done and the main tradeoffs. 
If you can not imagine how you could achieve the same result yourself (whatever the work necessary required), please do not use it.

## Examples

### Case: ORM 
If you do not understand that every EAGER relation will generate a full select of all the children probably you'll end loading full database in memory

### Case: Inversion of control
Dependency injection can be a powerful tool to avoid unnecessary boilerplate, but also can be harmful for novice developers, as they do not understand how it works internally:
* How much instances exist
* If you have the required context to allow injection.

