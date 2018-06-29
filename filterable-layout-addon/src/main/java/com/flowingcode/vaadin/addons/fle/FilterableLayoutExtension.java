package com.flowingcode.vaadin.addons.fle;

/*-
 * #%L
 * Filterable Layout Add-on
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

import java.util.Iterator;

import com.vaadin.server.AbstractExtension;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TextField;


/**
 * Main class of the addon.
 * 
 * @author mlopez
 *
 */
@SuppressWarnings("serial")
public class FilterableLayoutExtension extends AbstractExtension {
	
	private String filteredStyleName=null;
	
	private boolean hideFilteredComponents = true;
	
	public FilterableLayoutExtension(TextField textField, AbstractLayout layout) {
		textField.addValueChangeListener(e->{
			filterComponents(e.getValue(), layout);
		});
	}
	
	public void filterComponents(String filter, AbstractLayout layout) {
		Iterator<Component> it = layout.iterator();
		hideFilteredComponents(filter, it);
	}

	/**
	 * @param filter
	 * @param iterator
	 * @return false if all of the components where filtered
	 */
	private boolean hideFilteredComponents(String filter, Iterator<Component> iterator) {
		boolean result = false;
		while (iterator.hasNext()) {
			boolean removeStyle=false;
			Component component = (Component) iterator.next();
			if (component instanceof TabSheet) {
				TabSheet ts = (TabSheet) component;
				for(int i=0;i<ts.getComponentCount();i++) {
					Tab t = ts.getTab(i);
					if (t.getComponent() instanceof HasComponents) {
						boolean visible = hideFilteredComponents(filter, ((HasComponents)t.getComponent()).iterator());
						if (hideFilteredComponents) t.setVisible(visible); else t.setVisible(true);
						result = visible;
					} else {
						if (applyFilter(filter,component.getCaption()) ) {
							component.setVisible(true);
							if (hideFilteredComponents) t.setVisible(true); else t.setVisible(true);
							result = true;
						}
					}
				}
			} else if (component instanceof HasComponents) {
				HasComponents hc = (HasComponents) component;
				boolean visible = hideFilteredComponents(filter, hc.iterator());
				if (hideFilteredComponents) component.setVisible(visible); else component.setVisible(true);
				result = visible;
			} else if (applyFilter(filter,component.getCaption()) ) {
				if (hideFilteredComponents) component.setVisible(true); else component.setVisible(true);
				result = true;
			} else {
				if (hideFilteredComponents) component.setVisible(false); else component.setVisible(true);
				removeStyle=true;
			}
			if (filteredStyleName!=null && result && filter!=null && !filter.equals("") && !removeStyle)
				component.addStyleName(filteredStyleName);
			else
				component.removeStyleName(filteredStyleName);
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
	protected boolean applyFilter(String filter, String caption) {
		if (filter==null || caption==null || "".equals(filter))
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
