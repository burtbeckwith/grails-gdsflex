<?xml version="1.0" encoding="utf-8"?>

<mx:Application
    xmlns:mx="http://www.adobe.com/2006/mxml"
    xmlns:ui="org.granite.tide.uibuilder.*"
    xmlns="*"
    layout="vertical"
    backgroundGradientColors="[#0e2e7d, #6479ab]"
    preinitialize="Spring.getInstance().initApplication()">
   
    <mx:Script>
        <![CDATA[
        	import org.granite.tide.validators.ValidatorExceptionHandler;
        	import org.granite.tide.spring.Context;
            import org.granite.tide.spring.Spring;
            import org.granite.tide.uibuilder.GrailsEntityMetadataBuilder;
            import org.granite.tide.uibuilder.DefaultUIBuilder;
            import org.granite.tide.uibuilder.DefaultUIFormLayout;
            <% domainClassList.each { domainClass -> %>
            import ${domainClass};<% } %>
            
            [Bindable]
            private var context:Context = Spring.getInstance().getSpringContext();
            
            Spring.getInstance().addComponents([GrailsEntityMetadataBuilder, DefaultUIBuilder, DefaultUIFormLayout]);
            Spring.getInstance().addExceptionHandler(ValidatorExceptionHandler);
        ]]>
    </mx:Script>

    <mx:VBox id="mainUI" width="100%" height="100%">
        <mx:ApplicationControlBar id="acb" width="100%">
            <mx:Label text="GraniteDS / Grails generated application" fontSize="18" fontWeight="bold" color="#f0f0f0"/>
        </mx:ApplicationControlBar>
	   	
		<mx:HBox width="100%" height="100%">
			<mx:Panel width="200" height="100%" title="Controllers" paddingTop="4" paddingBottom="4"><%
            domainClassList.each { domainClass -> %>
				<mx:LinkButton label="${domainClass.substring(domainClass.indexOf(".")+1)}s" width="100%" textAlign="left"
					click="mainStack.selectedChild = ${domainClass.substring(domainClass.indexOf(".")+1).toLowerCase()}UI" />
            <% } %>
			</mx:Panel>
	
	        <mx:ViewStack id="mainStack" width="100%" height="100%"><%
            	domainClassList.each { domainClass -> %>
			    <ui:EntityUI id="${domainClass.substring(domainClass.indexOf(".")+1).toLowerCase()}UI" context="{context}" 
			    	entityClass="{${domainClass.substring(domainClass.indexOf(".")+1)}}" 
			    	width="100%" height="100%"/>
	            <% } %>
			</mx:ViewStack>
		</mx:HBox>
    </mx:VBox>
</mx:Application>