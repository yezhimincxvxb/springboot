package com.yzm.designmode.V迭代器Iterator;

import java.util.ArrayList;
import java.util.List;

public class IteratorClient {
    public static void main(String[] args) {
        Container container = new ConcreteContainer();
        container.add("AAA");
        container.add("BBB");
        container.add("CCC");
        container.add("DDD");
        container.add("EEE");

        Iterator iterator = container.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}

//容器角色
interface Container {
    void add(Object obj);

    void remove(Object obj);

    Iterator iterator();
}

//迭代器角色
interface Iterator {
    Object first();

    boolean hasNext();

    Object next();
}

//具体容器角色（Concrete Container）
class ConcreteContainer implements Container {
    private final List<Object> list;

    public ConcreteContainer() {
        this.list = new ArrayList<>();
    }

    public ConcreteContainer(List<Object> list) {
        this.list = list;
    }

    @Override
    public void add(Object obj) {
        list.add(obj);
    }

    @Override
    public void remove(Object obj) {
        list.remove(obj);
    }

    @Override
    public Iterator iterator() {
        return new ConcreteIterator();
    }

    //具体迭代器角色（Concrete Iterator）
    class ConcreteIterator implements Iterator {
        private int position = 0;

        public ConcreteIterator() {
        }

        @Override
        public Object first() {
            position = 0;
            return list.get(position);
        }

        @Override
        public boolean hasNext() {
            return position != list.size();
        }

        @Override
        public Object next() {
            Object ret = null;
            if (hasNext()) {
                ret = list.get(position);
            }
            position++;
            return ret;
        }
    }
}