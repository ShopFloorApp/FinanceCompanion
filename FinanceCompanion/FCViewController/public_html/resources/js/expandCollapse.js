(function () {
    var timeout;

    try {
        // TypeHandler for custom "expandcollapse" tags:
        var expandcollapse = adf.mf.api.amx.TypeHandler.register("http://xmlns.example.com/custom", "expandcollapse");

        expandcollapse.prototype._toggle = function (event) {

            var anchorElementId = event.data["anchorElement.id"];
            var tImg = document.getElementById(anchorElementId);

            var childRootElementId = event.data["childRootElement.id"];
            var ele = document.getElementById(childRootElementId);
            if ((ele.style.display == "block") || (ele.style.display == "")) {
                ele.style.display = "none";
                tImg.className = "toggle_button_collapsed";               
            }
            else {
                ele.style.display = "block";
                tImg.className = "toggle_button_expanded";               
            }

        };

        expandcollapse.prototype._renderHeaderFacet = function (amxNode, rootElementWrapper, tableContent, id) {
            var headerFacetChildren = amxNode.getRenderedChildren("header");
            if (headerFacetChildren.length > 0) {
                var header = document.createElement("div");// this is the table
                header.className = "div-table";
                rootElementWrapper.appendChild(header);

                var tableRow = document.createElement("div");// this is the row
                tableRow.className = "div-table-row";
                header.appendChild(tableRow);


                var divImg = document.createElement("div");// this is the first col in the row
                divImg.id=id+"_anchorImage";
                divImg.className = "div-table-col1";
                tableRow.appendChild(divImg);

                var divAnchor = document.createElement("a");// 
                divAnchor.id = id+"_header-expand-collapse-anchor";
                divAnchor.href = "#";
                divImg.appendChild(divAnchor);
                divAnchor.className = "toggle_button_expanded";
                
                var divCol2 = document.createElement("div");// this is the second col in the row
                divCol2.className = "div-table-col2";
                tableRow.appendChild(divCol2);

                var divCol2Header = document.createElement("div");// this is the first col
                divCol2Header.className = "header_text";
                divCol2.appendChild(divCol2Header);
           
                
                for (var i = 0, size = headerFacetChildren.length;i < size;++i) {
                    var childElement = headerFacetChildren[i].render();
                    if (childElement)
                        divCol2Header.appendChild(childElement);
                }
                



                adf.mf.api.amx.addBubbleEventListener(divAnchor, "tap", this._toggle, 
                {
                    "childRootElement.id" : tableContent.id, "anchorElement.id" : divAnchor.id 
                });
            }
        };

        /**
         * This method renders the expandcollapse component
         */
        expandcollapse.prototype.render = function (amxNode, id) {
            var rootElementWrapper = document.createElement("div");
            rootElementWrapper.className = "custom-expandcollapse";
            var tableContent = document.createElement("div");
            tableContent.id = id+"_custom-expandcollapse-content";
            tableContent.className="div-table-content";
            this._renderHeaderFacet(amxNode, rootElementWrapper, tableContent, id);
            try {
                var descendants = amxNode.renderDescendants();
                for (var i = 0, size = descendants.length;i < size;++i) {
                    var elem = descendants[i];
                    tableContent.appendChild(elem);
                }
            }
            catch (problem) {
                adf.mf.log.Framework.logp(adf.mf.log.level.SEVERE, "example", "render", "Problem with custom component creation: " + problem);
            }
            rootElementWrapper.appendChild(tableContent);
            return rootElementWrapper;
        };

    }
    catch (problem) {
        adf.mf.log.Framework.logp(adf.mf.log.level.SEVERE, "expandcollapse.js", "*", "Problem with custom code: " + problem);
    }

})();