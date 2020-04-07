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


import java.util.HashMap;

import java.util.Iterator;
import java.util.Map;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Element;


/**
 * Main class of the addon.
 * 
 * @author mlopez
 *
 */
public class FilterableLayoutExtension {
	
	private String filteredStyleName=null;
	
	private Map<Element,Element> containersToFilter = new HashMap<>();
	
	private boolean hideFilteredComponents = true;
	
	public FilterableLayoutExtension(TextField textField, Element mainElement) {
		textField.addValueChangeListener(e->{
			filterComponents(e.getValue(), mainElement);
		});
	}
	
	public void filterComponents(String filter, Element mainElement) {
		Iterator<Element> it = mainElement.getChildren().iterator();
		hideFilteredElements(filter, it);
	}
	
	public void addContainerToFilter(Element triggerer, Element container) {
		containersToFilter.put(triggerer, container);
	}

	/**
	 * @param filter
	 * @param iterator
	 * @return false if all of the components where filtered
	 */
	private boolean hideFilteredElements(String filter, Iterator<Element> iterator) {
		boolean result = false;
		while (iterator.hasNext()) {
			boolean filteredContainer = false;
			Element element = iterator.next();
			if (element.getChildCount()>0) {
				Iterator<Element> it = element.getChildren().iterator();
				boolean visible = hideFilteredElements(filter, it);
				element.setVisible(visible);
				result = result | visible;
				filteredContainer = visible;
			}
			if (containersToFilter.containsKey(element)) {
				Iterator<Element> it = containersToFilter.get(element).getChildren().iterator();
				boolean visible = hideFilteredElements(filter, it);
				element.setVisible(visible);
				result = result | visible;
				filteredContainer = visible;
			}
			String captionToFilter = element.getProperty("label");
			if (captionToFilter==null) captionToFilter = element.getText();
			boolean filterMatched = filterMatch(filter,captionToFilter) || filteredContainer;
			try {
				boolean visible = filterMatched || !hideFilteredComponents;
				element.setVisible(visible);
				result = result || filterMatched;
				if (filteredStyleName!=null && filterMatched && filter!=null && !filter.equals("") && !filteredContainer) {
					element.getClassList().add(filteredStyleName);
				} else {
					element.getClassList().remove(filteredStyleName);
				}
			} catch (UnsupportedOperationException e) {
			}
		}
		return result;
	}

	/**
	 * Method that applies the filter to the component's caption
	 * 
	 * @param filter
	 * @param caption
	 * @return true if the filter matches the caption, so the component has to remain visible
	 */
	private boolean filterMatch(String filter, String caption) {
		if (filter==null || /*caption==null ||*/ "".equals(filter) /*|| "".equals(caption)*/)
			return true;
		return (caption.toLowerCase().indexOf(filter.toLowerCase())>-1);
	}

	public String getFilteredStyleName() {
		return filteredStyleName;
	}

	public void setFilteredStyleName(String filteredStyleName) {
		this.filteredStyleName = filteredStyleName;
	}

	public boolean isHideFilteredComponents() {
		return hideFilteredComponents;
	}

	public void setHideFilteredComponents(boolean hideFilteredComponents) {
		this.hideFilteredComponents = hideFilteredComponents;
	}
	
	
}
