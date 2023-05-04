### META FILE

```
<?xml version="1.0" encoding="UTF-8"?>
<LightningComponentBundle xmlns="http://soap.sforce.com/2006/04/metadata">
    <apiVersion>51.0</apiVersion>
    <isExposed>true</isExposed>
    <targets>
        <target>lightning__RecordPage</target>
        <target>lightning__HomePage</target>
       <target>lightning__AppPage</target>
    </targets>
</LightningComponentBundle>
```

### JS FILE

```
import { LightningElement,track } from 'lwc';

export default class SimpleLWC1 extends LightningElement {
@track currentOutput;
netProfit;
totalRevenue;

netProfitChangeHandler(event)
    {
         this.netProfit=parseInt(event.target.value);
    }
totalRevenueChangeHandler(event)
    {
         this.totalRevenue=parseInt(event.target.value);
    }
calculateNPMHandler()
    {
        this.currentOutput='The Net Profit Margin is : '+(this.netProfit/this.totalRevenue)*100; 
    }
}
```

### HTML FILE

```
<template>
    <lightning-card title="Simple Net Profit Margin Calculator">
      <lightning-layout multiple-rows>

            <lightning-layout-item size="12" padding="around-medium">
                <lightning-input type="number" label="Enter Net Profit" onchange= {netProfitChangeHandler}></lightning-input>
                </lightning-layout-item>

            <lightning-layout-item size="12" padding="around-medium">
                <lightning-input type="number" label="Enter Total Revenue" onchange= {totalRevenueChangeHandler}></lightning-input>
                </lightning-layout-item>

            <lightning-layout-item size="12" padding="around-medium">   
                <lightning-button label="Calculate NPM" icon-position="center" onclick={calculateNPMHandler}></lightning-button>
                </lightning-layout-item>  

            <lightning-layout-item size="12" padding="around-medium">
                <lightning-formatted-text value={currentOutput}></lightning-formatted-text>
                </lightning-layout-item>


        </lightning-layout>
     </lightning-card>
</template>
```
