package com.flowingcode.vaadin.addons.fle.demo;

/*-
 * #%L
 * Filterable Layout Add-on Demo
 * %%
 * Copyright (C) 2018 Flowing Code
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.Arrays;

import javax.servlet.annotation.WebServlet;

import com.flowingcode.vaadin.addons.fle.FilterableLayoutExtension;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Theme("demo")
@Title("Filterable Layout Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI
{

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
    
    	Window w = new Window("Filterable Layout Demo");
        w.setWidth("1100px");
        w.setHeight("700px");
        int height = Page.getCurrent().getBrowserWindowHeight();
        int width = Page.getCurrent().getBrowserWindowWidth();
        w.setPosition(((int)(width-w.getWidth())/2), ((int)(height - w.getHeight())/2));
        
        VerticalLayout content = new VerticalLayout();
        content.setSpacing(true);
    	
        VerticalLayout vl = new VerticalLayout();
        TabSheet ts = new TabSheet();
        ts.addTab(createTab1(), "Employee Information");
        ts.addTab(createTab2(), "Company Information");
        ts.addTab(createTab3(), "Sales Information");
        ts.setWidth("100%");
        vl.addComponent(ts);
        GridLayout gl = new GridLayout();
        gl.setWidth("100%");
        gl.setColumns(2);
        gl.setRows(3);
        gl.addComponent(createTextField("Identification number"),0,0);
        gl.addComponent(createComboBox("Information Type"),1,0);
        gl.addComponent(createTextField("Comment"),0,1);
        gl.addComponent(createTextField("EMail"),1,1);
        gl.addComponent(createDateField("Birthday"),0,2);
        gl.setSpacing(true);
        vl.addComponent(gl);
        vl.setSizeFull();
        vl.setMargin(false);

        TextField filter = createTextField("Filter");
        FilterableLayoutExtension flayout = new FilterableLayoutExtension(filter,vl);
        flayout.setFilteredStyleName("filtered");
        filter.setWidth("100%");
        CheckBox cb = new CheckBox("Hide filtered components");
        cb.setValue(true);
        cb.addValueChangeListener(e->{
        	flayout.setHideFilteredComponents(e.getValue());
        	flayout.filterComponents(filter.getValue(), vl);
        });

        content.addComponents(filter,cb,vl);
        
        w.setContent(content);

        UI.getCurrent().addWindow(w);
    }

	private Component createDateField(String string) {
		DateField result = new DateField(string);
		result.setWidth("100%");
		return result;
	}

	private Component createComboBox(String string) {
		ComboBox<String> result = new ComboBox<String>(string, Arrays.asList(new String[] {"Option 1", "Option 2", "Option 3"}));
		result.setWidth("100%");
		return result;
	}

	private TextField createTextField(String string) {
		TextField result = new TextField(string);
		result.setWidth("100%");
		return result;
	}

	private Component createTab3() {
        GridLayout gl = new GridLayout();
        gl.setWidth("100%");
        gl.setColumns(2);
        gl.setRows(3);
        gl.addComponent(createTextField("Sales contact"),0,0);
        gl.addComponent(createComboBox("Sales size"),1,0);
        gl.addComponent(createTextField("Comments"),0,1);
        gl.addComponent(createTextField("Senders address"),1,1);
        gl.addComponent(createDateField("Sale date"),0,2);
        gl.setSpacing(true);
		return gl;
	}

	private Component createTab2() {
        GridLayout gl = new GridLayout();
        gl.setWidth("100%");
        gl.setColumns(2);
        gl.setRows(3);
        gl.addComponent(createTextField("Company name"),0,0);
        gl.addComponent(createComboBox("Sector"),1,0);
        gl.addComponent(createTextField("Tax ID"),0,1);
        gl.addComponent(createTextField("Address"),1,1);
        gl.addComponent(createDateField("Start Date"),0,2);
        gl.setSpacing(true);
		return gl;
	}

	private Component createTab1() {
        GridLayout gl = new GridLayout();
        gl.setWidth("100%");
        gl.setColumns(2);
        gl.setRows(3);
        gl.addComponent(createTextField("Name"),0,0);
        gl.addComponent(createComboBox("Customer type"),1,0);
        gl.addComponent(createTextField("Address"),0,1);
        gl.addComponent(createTextField("City"),1,1);
        gl.addComponent(createDateField("Birthday"),0,2);
        gl.setSpacing(true);
		return gl;
	}
}
