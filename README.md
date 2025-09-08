[![Published on Vaadin Directory](https://img.shields.io/badge/Vaadin%20Directory-published-00b4f0.svg)](https://vaadin.com/directory/component/filterable-layout-add-on)
[![Stars on vaadin.com/directory](https://img.shields.io/vaadin-directory/star/filterable-layout-add-on.svg)](https://vaadin.com/directory/component/filterable-layout-add-on)
[![Build Status](https://jenkins.flowingcode.com/buildStatus/icon?job=FilterableLayout-14-addon)](https://jenkins.flowingcode.com/job/FilterableLayout-14-addon)
[![Javadoc](https://img.shields.io/badge/javadoc-00b4f0)](https://javadoc.flowingcode.com/artifact/com.flowingcode.vaadin.addons/filterable-layout-addon)

# Filterable Layout Add-on for Vaadin 14+

Filterable Layout is an extension for Vaadin 14+, that allows you to build forms that can be filtered to only display components with a certain caption.

## Online demo

[Online demo here](http://addonsv14.flowingcode.com/filterablelayout)

## Download release

[Available in Vaadin Directory](https://vaadin.com/directory/component/filterable-layout-add-on)

## Building and running demo

- git clone repository
- mvn clean install jetty:run

To see the demo, navigate to http://localhost:8080/

## Release notes

- **Version 2.0.0** Initial release for Vaadin 14+ NPM mode

## Roadmap

* Support for more container components

## Issue tracking

The issues for this add-on are tracked on its github.com page. All bug reports and feature requests are appreciated. 

## Contributions

Contributions are welcome. There are two primary ways you can contribute: by reporting issues or by submitting code changes through pull requests. To ensure a smooth and effective process for everyone, please follow the guidelines below for the type of contribution you are making.

#### 1. Reporting Bugs and Requesting Features

Creating an issue is a highly valuable contribution. If you've found a bug or have an idea for a new feature, this is the place to start.

* Before creating an issue, please check the existing issues to see if your topic is already being discussed.
* If not, create a new issue, choosing the right option: "Bug Report" or "Feature Request". Try to keep the scope minimal but as detailed as possible.

> **A Note on Bug Reports**
> 
> Please complete all the requested fields to the best of your ability. Each piece of information, like the environment versions and a clear description, helps us understand the context of the issue.
> 
> While all details are important, the **[minimal, reproducible example](https://stackoverflow.com/help/minimal-reproducible-example)** is the most critical part of your report. It's essential because it removes ambiguity and allows our team to observe the problem firsthand, exactly as you are experiencing it.

#### 2. Contributing Code via Pull Requests

As a first step, please refer to our [Development Conventions](https://github.com/FlowingCode/DevelopmentConventions) page to find information about Conventional Commits & Code Style requirements.

Then, follow these steps for creating a contribution:
 
- Fork this project.
- Create an issue to this project about the contribution (bug or feature) if there is no such issue about it already. Try to keep the scope minimal.
- Develop and test the fix or functionality carefully. Only include minimum amount of code needed to fix the issue.
- For commit message, use [Conventional Commits](https://github.com/FlowingCode/DevelopmentConventions/blob/main/conventional-commits.md) to describe your change.
- Send a pull request for the original project.
- Comment on the original issue that you have implemented a fix for it.

## License & Author

This add-on is distributed under Apache License 2.0. For license terms, see LICENSE.txt.

Filterable Layout Add-on is written by Flowing Code S.A.

# Developer Guide

## Getting started

### Instantiating

        VerticalLayout vl = new VerticalLayout();
		...
		TextField filter = createTextField("Filter");
		FilterableLayoutExtension flayout = new FilterableLayoutExtension(filter, vl.getElement());

### Adding a filtered style

		flayout.setFilteredStyleName("filtered");

### Configuring it to hide or not filtered components

		Checkbox cb = new Checkbox("Hide filtered components");
		cb.setValue(true);
		cb.addValueChangeListener(e -> {
			flayout.setHideFilteredComponents(e.getValue());
			flayout.filterComponents(filter.getValue(), vl.getElement());
		});




