package gwtquery.plugins.selectable.client;

import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;

public class SelectableOptions {
    public static enum Tolerance {
	TOUCH, FIT;
    }

    /**
     * Disables (true) or enables (false) the selectable. Can be set when
     * initialising (first creating) the selectable. Default : 'false'
     */
    private boolean disabled;

    /**
     * This determines whether to refresh (recalculate) the position and size of
     * each selectee at the beginning of each select operation. If you have many
     * many items, you may want to set this to false and call the refresh method
     * manually. Default : 'true'
     */
    private boolean autoRefresh;

    /**
     * Selector used to append the 'lasso' div Default : 'body'
     */
    private String appendTo; // maybe GQuery instead

    /**
     * Time in milliseconds to define when the selecting should start. It helps
     * preventing unwanted selections when clicking on an element. Default : 0
     * 
     * NOT IMPLEMENTED YET !!!!
     */
    private int delay;

    /**
     * Tolerance, in pixels, for when selecting should start. If specified,
     * selecting will not start until after mouse is dragged beyond distance.
     * Default : 0
     * 
     * NOT IMPLEMENTED YET !!!!
     */
    private int distance;

    /**
     * The matching child elements will be made selectees (able to be selected)
     * Default : '*'
     */
    private String filter;

    /**
     * Tolerance to select an selectee. Default : TOUCH
     */
    private Tolerance tolerance;

    
    /**
     * This callback function is called at the end of the select operation, on each element added to the selection.
     */
    private Function onSelected;
    
    /**
     * This callback function is called during the select operation, on each element added to the selection.
     */
    private Function onSelecting;

    /**
     * This callback function is called at the end of the select operation, on each element added to the selection.
     */
    private Function onUnselected;

    /**
     * This callback function is called during the select operation, on each element removed from the selection.
     */
    private Function onUnselecting;

    /**
     * This callback function is called at the end of the select operation, on each element removed from the selection.
     */
    private Function onStartSelection;

    /**
     * This callback function is called at the end of the select operation.
     */
    private Function onStopSelection;

    public SelectableOptions() {
	initDefault();
    }

    private void initDefault() {
	disabled = false;
	autoRefresh = true;
	delay = 0;
	distance = 0;
	filter = "*";
	tolerance = Tolerance.TOUCH;
	appendTo = GQuery.body.getTagName();
    }

    public boolean isDisabled() {
	return disabled;
    }

    public void setDisabled(boolean disabled) {
	this.disabled = disabled;
    }

    public boolean isAutoRefresh() {
	return autoRefresh;
    }

    public void setAutoRefresh(boolean autoRefresh) {
	this.autoRefresh = autoRefresh;
    }

    public String getAppendTo() {
	return appendTo;
    }

    public void setAppendTo(String appendTo) {
	this.appendTo = appendTo;
    }

    public int getDelay() {
	return delay;
    }

    public void setDelay(int delay) {
	this.delay = delay;
    }

    public int getDistance() {
	return distance;
    }

    public void setDistance(int distance) {
	this.distance = distance;
    }

    public String getFilter() {
	return filter;
    }

    public void setFilter(String filter) {
	this.filter = filter;
    }

    public Tolerance getTolerance() {
	return tolerance;
    }

    public void setTolerance(Tolerance tolerance) {
	this.tolerance = tolerance;
    }

    public Function getOnSelected() {
        return onSelected;
    }

    public void setOnSelected(Function onSelected) {
        this.onSelected = onSelected;
    }

    public Function getOnSelecting() {
        return onSelecting;
    }

    public void setOnSelecting(Function onSelecting) {
        this.onSelecting = onSelecting;
    }

    public Function getOnUnselected() {
        return onUnselected;
    }

    public void setOnUnselected(Function onUnselected) {
        this.onUnselected = onUnselected;
    }

    public Function getOnUnselecting() {
        return onUnselecting;
    }

    public void setOnUnselecting(Function onUnselecting) {
        this.onUnselecting = onUnselecting;
    }

    public Function getOnStartSelection() {
        return onStartSelection;
    }

    public void setOnStartSelection(Function onStartSelection) {
        this.onStartSelection = onStartSelection;
    }

    public Function getOnStopSelection() {
        return onStopSelection;
    }

    public void setOnStopSelection(Function onStopSelection) {
        this.onStopSelection = onStopSelection;
    }
    

}
