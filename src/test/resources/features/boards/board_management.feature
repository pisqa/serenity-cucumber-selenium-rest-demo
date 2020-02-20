Feature: Trello Board Management

 Scenario Outline: Templates (UI)
 	Given I am on the trello login page
	And I log into trello
	When I search for template '<template>'
	And I create new board '<board>' from the template
	Then the board should contain the lists '<lists>'
	And the activity log should contain the entry "copied this board from '<template>'"
	Examples:
		|board 		| template 					| lists |
		|demoKanban |  Kanban Template			| Backlog, Design,To Do, Doing, Code Review, Testing, Done |
		|demoBikes 	|  Bike Repair Pipeline 	| Needs Parts ordered, At Paint, Ready to Build, Wheel Builds, Services/Repairs |
		|demoRoadmap|  Product Roadmap 			| Ideas, Research and Design, Estimating, Sprint Candidates |
		|demoRetro 	|  Sprint Retrospectives	| Sprint Retrospectives Template:, Went Well, Needs To Change, Questions & Discussion,  Action Items |


Scenario: Perform various trello board actions and see the actions are correctly returned in the actions request (REST)
	Given I create a new board called 'demoBoard'
	And I create a list called 'ToDo' in the new board
	And I create a card called 'Do something useful' in the new list
	And I add comment 'is this useful enough?' to the new card
	And I add checklist 'demoChecklist' with item 'check1' to the card
	And I check the checklist item
	And I remove the checklist from the card
	And I add attachment url 'http://www.google.com' with name 'demoLink' to the card
	And I remove the attachment from the card
	And I close the card
	When I get the actions for the board
	Then the total number of actions should be 10
 	And the actions should include the following details:
 		|type 						| data 						|expectedValue				|
 		|createBoard   				| data.board.name			|demoBoard 					|
 		|createList   				| data.list.name			|ToDo 						|
 		|createCard   		 		| data.card.name    		|Do something useful    	|
 		|commentCard   				| data.text    				|is this useful enough?		|
		|addChecklistToCard   		| data.checklist.name		|demoChecklist				|
		|updateCheckItemStateOnCard | data.checkItem.state		|complete					|
		|removeChecklistFromCard 	| data.checklist.name		|demoChecklist				|
		|addAttachmentToCard 		| data.attachment.name		|demoLink						|
		|deleteAttachmentFromCard	| data.attachment.name		|demoLink						|
		|updateCard					| data.card.closed			|true						|

  

  