/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.stunner.client.widgets.editor;

import javax.annotation.PreDestroy;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.uberfire.client.workbench.widgets.ResizeFlowPanel;

@Dependent
@Templated
public class StunnerEditorView
        extends Composite
        implements RequiresResize,
                   ProvidesResize,
                   IsWidget {

    @DataField
    private ResizeFlowPanel editorPanel;

    protected StunnerEditorView() {
        //CDI proxy
    }

    @Inject
    public StunnerEditorView(final ResizeFlowPanel editorPanel) {
        this.editorPanel = editorPanel;
    }
    @Override
    protected void onAttach() {
        super.onAttach();
        if (getElement().getParentElement() != null) {
            getElement().getParentElement().getStyle().setHeight(100, Style.Unit.PCT);
            getElement().getParentElement().getStyle().setWidth(100, Style.Unit.PCT);
            getElement().getParentElement().getStyle().setDisplay(Style.Display.TABLE);
        }
    }

    @Override
    public void onResize() {
        editorPanel.onResize();
    }

    public void setWidget(final IsWidget widget) {
        clear();
        editorPanel.add(widget);
    }

    public void clear() {
        editorPanel.clear();
    }

    @PreDestroy
    public void destroy() {
        clear();
        editorPanel.removeFromParent();
    }
}
