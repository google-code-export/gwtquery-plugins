package gwtquery.plugins.draggable.client;

public class DraggableContainment {

  private String containment;
  private int[] containmentAsArray;

  public DraggableContainment(String selector) {
    containment = selector;
  }

  public DraggableContainment(int[] coordinates) {
    containmentAsArray = coordinates;
  }

  public int[] calculate() {
    // TODO implement this
    
    /*_setContainment: function() {

    var o = this.options;
    if(o.containment == 'parent') o.containment = this.helper[0].parentNode;
    if(o.containment == 'document' || o.containment == 'window') this.containment = [
      0 - this.offset.relative.left - this.offset.parent.left,
      0 - this.offset.relative.top - this.offset.parent.top,
      $(o.containment == 'document' ? document : window).width() - this.helperProportions.width - this.margins.left,
      ($(o.containment == 'document' ? document : window).height() || document.body.parentNode.scrollHeight) - this.helperProportions.height - this.margins.top
    ];

    if(!(/^(document|window|parent)$/).test(o.containment) && o.containment.constructor != Array) {
      var ce = $(o.containment)[0]; if(!ce) return;
      var co = $(o.containment).offset();
      var over = ($(ce).css("overflow") != 'hidden');

      this.containment = [
        co.left + (parseInt($(ce).css("borderLeftWidth"),10) || 0) + (parseInt($(ce).css("paddingLeft"),10) || 0) - this.margins.left,
        co.top + (parseInt($(ce).css("borderTopWidth"),10) || 0) + (parseInt($(ce).css("paddingTop"),10) || 0) - this.margins.top,
        co.left+(over ? Math.max(ce.scrollWidth,ce.offsetWidth) : ce.offsetWidth) - (parseInt($(ce).css("borderLeftWidth"),10) || 0) - (parseInt($(ce).css("paddingRight"),10) || 0) - this.helperProportions.width - this.margins.left,
        co.top+(over ? Math.max(ce.scrollHeight,ce.offsetHeight) : ce.offsetHeight) - (parseInt($(ce).css("borderTopWidth"),10) || 0) - (parseInt($(ce).css("paddingBottom"),10) || 0) - this.helperProportions.height - this.margins.top
      ];
    } else if(o.containment.constructor == Array) {
      this.containment = o.containment;
    }

  },*/
    return null;
  }

}