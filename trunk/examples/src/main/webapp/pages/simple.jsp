<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
     <f:view>
         <head>
             <title>Welcome to GMaps4JSF</title>
             <m:resources key="ABQIAAAAD6cDv-a0AnpzXA4gj6utCRTUlDOzA1Sd8h2eDxLDJEZUtkHZ_xQlPmNUQ-At6YLqCd29cGkwT8i95A"/>
         </head>
         <body>
             <h:form id="form">
                  <m:map width="500" height="450px" latitude="30.01" longitude="31.14" enableScrollWheelZoom="true">
                      <m:marker>
                          <m:htmlInformationWindow htmlText="This is Cairo, Egypt" />
                      </m:marker>
                  </m:map>
             </h:form>
             <%@include file="../templates/footer.jspf" %>
         </body>
     </f:view>    
</html>