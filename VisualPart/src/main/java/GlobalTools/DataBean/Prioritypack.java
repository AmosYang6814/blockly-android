package GlobalTools.DataBean;

import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2020/2/14.
 */

interface priority<T>{
    public void increaseCount();
    public int getCount();
    public void setCount(int count);
}

public class Prioritypack<T> implements priority,Comparable<Prioritypack<T>>{
    int count;
    T object;

    public Prioritypack(int count, T object) {
        this.count = count;
        this.object = object;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void increaseCount(){
        count=count+1;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }


    @Override
    public int compareTo(@NonNull Prioritypack<T> o) {
        return getCount() > o.getCount() ? -1 : (getCount() == o.getCount() ? 0 : 1);
    }
}
