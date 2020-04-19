package UI.EvenHanding;

import android.view.View;

import java.util.List;

import GlobalTools.DataBean.Action.ActionMean;
import GlobalTools.DataBean.Action.EventType;
import GlobalTools.DataBean.UiComponent;

public interface BuilderAction<T> {
      void builderAttributeChage(String screenName, UiComponent uiComponent, UiComponent uiComponentChange, EventType eventType);
      void builderJumpEvent(String current,String next, UiComponent uiComponent,EventType eventType);
      void builderwakeModule(String screenName,UiComponent uiComponent,EventType eventType);
}
