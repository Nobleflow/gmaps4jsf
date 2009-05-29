/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.googlecode.gmaps4jsf.component.map;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.googlecode.gmaps4jsf.component.eventlistener.EventListener;
import com.googlecode.gmaps4jsf.component.point.Point;
import com.googlecode.gmaps4jsf.component.polygon.Polygon;

public class PolygonEncoder {

    public PolygonEncoder() {
    }

    private static void encodePolygon(FacesContext facesContext,
	    Map mapComponent, Polygon polygon, ResponseWriter writer)
	    throws IOException {
	
	String polygonLinesStr = "";
	Iterator iterator = polygon.getChildren().iterator();
	
	do {
	    if (!iterator.hasNext()) {
		break;
	    }
	    
	    UIComponent component = (UIComponent) iterator.next();
	    
	    if (component instanceof Point) {
		Point point = (Point) component;
		
		if (!polygonLinesStr.equals("")) {
		    polygonLinesStr = polygonLinesStr + ",";
		}
		
		polygonLinesStr = polygonLinesStr + "new GLatLng("
			+ point.getLatitude() + ", " + point.getLongitude()
			+ ")";
	    }
	} while (true);
	
	writer.write("var polygon_" + polygon.getId() + "  = new " + "GPolygon"
		+ "([" + polygonLinesStr + "], \""
		+ polygon.getHexStrokeColor() + "\", " + polygon.getLineWidth()
		+ "," + polygon.getStrokeOpacity() + ", \""
		+ polygon.getHexFillColor() + "\", " + polygon.getFillOpacity()
		+ ");");
	writer.write("map_base_variable.addOverlay(polygon_" + polygon.getId()
		+ ");");
	
	iterator = polygon.getChildren().iterator();
	
	do {
	    if (!iterator.hasNext()) {
		break;
	    }
	    
	    UIComponent component = (UIComponent) iterator.next();
	    
	    if (component instanceof EventListener) {
		EventEncoder.encodeEventListenersFunctionScript(facesContext,
			polygon, writer, "polygon_" + polygon.getId());
		EventEncoder.encodeEventListenersFunctionScriptCall(
			facesContext, polygon, writer, "polygon_"
				+ polygon.getId());
	    }
	} while (true);
	
	updatePolygonJSVariable(facesContext, polygon, writer);
    }

    private static void updatePolygonJSVariable(FacesContext facesContext,
	    Polygon polygon, ResponseWriter writer) throws IOException {
	
	if (polygon.getJsVariable() != null) {
	    writer.write("\r\n" + polygon.getJsVariable() + " = " + "polygon_"
		    + polygon.getId() + ";\r\n");
	}
    }

    public static void encodePolygonsFunctionScript(FacesContext facesContext,
	    Map mapComponent, ResponseWriter writer) throws IOException {
	
	writer.write("function createPolygonsFunction" + mapComponent.getId()
		+ "(" + "map_base_variable" + ") {");
	Iterator iterator = mapComponent.getChildren().iterator();
	
	do {
	    if (!iterator.hasNext()) {
		break;
	    }
	    
	    UIComponent component = (UIComponent) iterator.next();
	    
	    if ((component instanceof Polygon) && component.isRendered()) {
		encodePolygon(facesContext, mapComponent, (Polygon) component,
			writer);
	    }
	} while (true);
	writer.write("}");
    }

    public static void encodePolygonsFunctionScriptCall(
	    FacesContext facesContext, Map mapComponent, ResponseWriter writer)
	    throws IOException {
	
	writer.write("createPolygonsFunction" + mapComponent.getId() + "("
		+ "map_base_variable" + ");");
    }
}