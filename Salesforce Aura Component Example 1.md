### APXC FILE 

```
public class MyOpportunityListController1 {
@AuraEnabled
public static List<Opportunity> getOpportunities(Id recordId) {
   return [SELECT Id, Name, Amount, CloseDate FROM Opportunity WHERE AccountId = :recordId];
}
}
```



### CMP FILE

```
<aura:component controller="MyOpportunityListController1" implements="flexipage:availableForRecordHome,force:hasRecordId" access="global" >
    <aura:attribute name="recordId" type="Id" />
    <aura:attribute name="Account" type="Account" />
    <aura:attribute name="Opportunities" type="Opportunity" />
    <aura:attribute name="Columns" type="List" />
    <aura:handler name="init" value="{!this}" action="{!c.myAction}" />
    <force:recordData aura:id="accountRecord"
                      recordId="{!v.recordId}"
                      targetFields="{!v.Account}"
                      layoutType="FULL"
                      />
    <lightning:card iconName="standard:opportunity" title="{! 'Opportunity List for ' + v.Account.Name}">
    	<!-- Opportunity List goes here -->
        <lightning:datatable data="{! v.Opportunities }" columns="{! v.Columns }" keyField="Id" hideCheckboxColumn="true"/>
    </lightning:card>
	
</aura:component>
```



### JS FILE

```
({
	myAction : function(component, event, helper) {
        component.set("v.Columns", [
    {label:"Name", fieldName:"Name", type:"text(120)"},
    {label:"Amount", fieldName:"Amount", type:"Currency(16,2)"},
    {label:"Close Date", fieldName:"CloseDate", type:"Date"}
]);
var action = component.get("c.getOpportunities");
action.setParams({
    recordId: component.get("v.recordId")
});
action.setCallback(this, function(data) {
    component.set("v.Opportunities", data.getReturnValue());
});
$A.enqueueAction(action);		
	}
})
```
