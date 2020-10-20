/*-
 * #%L
 * Filterable Layout Add-on
 * %%
 * Copyright (C) 2018 - 2020 Flowing Code
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
package com.flowingcode.vaadin.addons.fle;

import com.flowingcode.vaadin.addons.DemoLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import java.util.Arrays;

@SuppressWarnings("serial")
@Route(value = "filterablelayout", layout = DemoLayout.class)
@StyleSheet("context://frontend/styles/filterable-layout/demo-styles.css")
public class FilterablelayoutDemoView extends VerticalLayout {
	private static final String SALES_INFORMATION = "Sales Information";
	private static final String COMPANY_INFORMATION = "Company Information";
	private static final String EMPLOYEE_INFORMATION = "Employee Information";

	public FilterablelayoutDemoView() {
		VerticalLayout content = new VerticalLayout();
		content.setSpacing(true);

		VerticalLayout vl = new VerticalLayout();
		Tabs ts = new Tabs();
		Component tab1Content = createTab1();
		Component tab2Content = createTab2();
		Component tab3Content = createTab3();
		VerticalLayout tabContent = new VerticalLayout();
		tabContent.add(tab1Content);

		Tab tab1 = new Tab(EMPLOYEE_INFORMATION);
		Tab tab2 = new Tab(COMPANY_INFORMATION);
		Tab tab3 = new Tab(SALES_INFORMATION);
		ts.add(tab1);
		ts.add(tab2);
		ts.add(tab3);
		ts.addSelectedChangeListener(ev -> {
			switch (ev.getSelectedTab().getLabel()) {
			case EMPLOYEE_INFORMATION:
				tabContent.removeAll();
				tabContent.add(tab1Content);
				break;
			case COMPANY_INFORMATION:
				tabContent.removeAll();
				tabContent.add(tab2Content);
				break;
			case SALES_INFORMATION:
				tabContent.removeAll();
				tabContent.add(tab3Content);
				break;
			default:
				break;
			}
		});
		ts.setWidth("100%");
		vl.add(ts);
		vl.add(tabContent);
		FormLayout gl = new FormLayout();
		gl.setWidth("100%");
		gl.add(createTextField("Identification number"));
		gl.add(createComboBox("Information Type"));
		gl.add(createTextField("Comment"));
		gl.add(createTextField("EMail"));
		gl.add(createDateField("Birthday"));
		vl.add(gl);
		vl.setSizeFull();
		vl.setMargin(false);

		TextField filter = createTextField("Filter");
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.setValueChangeTimeout(500);
		FilterableLayoutExtension flayout = new FilterableLayoutExtension(filter, vl.getElement());
		flayout.addContainerToFilter(tab1.getElement(), tab1Content.getElement());
		flayout.addContainerToFilter(tab2.getElement(), tab2Content.getElement());
		flayout.addContainerToFilter(tab3.getElement(), tab3Content.getElement());
		flayout.setFilteredStyleName("filtered");
		filter.setWidth("100%");
		Checkbox cb = new Checkbox("Hide filtered components");
		cb.setValue(true);
		cb.addValueChangeListener(e -> {
			flayout.setHideFilteredComponents(e.getValue());
			flayout.filterComponents(filter.getValue(), vl.getElement());
		});

		content.add(filter, cb, vl);
		add(content);
	}

	private Component createDateField(String string) {
		DatePicker result = new DatePicker(string);
		result.setWidth("100%");
		return result;
	}

	private Component createComboBox(String string) {
		ComboBox<String> result = new ComboBox<String>(string,
				Arrays.asList(new String[] { "Option 1", "Option 2", "Option 3" }));
		result.setWidth("100%");
		return result;
	}

	private TextField createTextField(String string) {
		TextField result = new TextField(string);
		result.setWidth("100%");
		return result;
	}

	private Component createTab3() {
		FormLayout fl = new FormLayout();
		fl.setWidth("100%");
		fl.add(createTextField("Sales contact"));
		fl.add(createComboBox("Sales size"));
		fl.add(createTextField("Comments"));
		fl.add(createTextField("Senders address"));
		fl.add(createDateField("Sale date"));
		return fl;
	}

	private Component createTab2() {
		FormLayout fl = new FormLayout();
		fl.setWidth("100%");
		fl.add(createTextField("Company name"));
		fl.add(createComboBox("Sector"));
		fl.add(createTextField("Tax ID"));
		fl.add(createTextField("Address"));
		fl.add(createDateField("Start Date"));
		return fl;
	}

	private Component createTab1() {
		FormLayout fl = new FormLayout();
		fl.setWidth("100%");
		fl.add(createTextField("Name"));
		fl.add(createComboBox("Customer type"));
		fl.add(createTextField("Address"));
		fl.add(createTextField("City"));
		fl.add(createDateField("Birthday"));
		return fl;
	}
}
