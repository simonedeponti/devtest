package com.example.devtest.controllers;

import com.example.devtest.beans.Page;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AbstractPagedControllerTest {

    static class Bean {
        String name;

        Bean(String name) {
            this.name = name;
        }
    }

    static class TestController extends AbstractPagedController<Bean> {}

    List<Bean> getXBeans(int x) {
        return IntStream.rangeClosed(1, x)
                .mapToObj(Integer::toString)
                .map(Bean::new)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("rawtypes")
    static int countIterable(Iterable iterable) {
        int counter = 0;
        for (Object ignored : iterable) {
            counter++;
        }
        return counter;
    }

    @Test
    void testGetPageOOTB() {
        TestController controller = new TestController();
        Page<Bean> page;

        // tests with 101 items, 10 items per page, testing start/middle/end
        page = controller.getPage(
                new PageImpl<>(
                        getXBeans(10),
                        PageRequest.of(0, 10),
                        101));
        assertEquals(10, page.getPageSize());
        assertEquals(0, page.getPageNumber());
        assertEquals(10, countIterable(page.getItems()));
        assertEquals(11, page.getTotalPages());
        assertEquals(101, page.getTotalItems());

        page = controller.getPage(
                new PageImpl<>(
                        getXBeans(10),
                        PageRequest.of(5, 10),
                        101));
        assertEquals(10, page.getPageSize());
        assertEquals(5, page.getPageNumber());
        assertEquals(10, countIterable(page.getItems()));
        assertEquals(11, page.getTotalPages());
        assertEquals(101, page.getTotalItems());

        page = controller.getPage(
                new PageImpl<>(
                        getXBeans(1),
                        PageRequest.of(10, 10),
                        101));
        assertEquals(10, page.getPageSize());
        assertEquals(10, page.getPageNumber());
        assertEquals(1, countIterable(page.getItems()));
        assertEquals(11, page.getTotalPages());
        assertEquals(101, page.getTotalItems());

        // tests with 100 items, 10 items per page, testing start/middle/end
        page = controller.getPage(
                new PageImpl<>(
                        getXBeans(10),
                        PageRequest.of(0, 10),
                        100));
        assertEquals(10, page.getPageSize());
        assertEquals(0, page.getPageNumber());
        assertEquals(10, countIterable(page.getItems()));
        assertEquals(10, page.getTotalPages());
        assertEquals(100, page.getTotalItems());

        page = controller.getPage(
                new PageImpl<>(
                        getXBeans(10),
                        PageRequest.of(5, 10),
                        100));
        assertEquals(10, page.getPageSize());
        assertEquals(5, page.getPageNumber());
        assertEquals(10, countIterable(page.getItems()));
        assertEquals(10, page.getTotalPages());
        assertEquals(100, page.getTotalItems());

        page = controller.getPage(
                new PageImpl<>(
                        getXBeans(10),
                        PageRequest.of(9, 10),
                        100));
        assertEquals(10, page.getPageSize());
        assertEquals(9, page.getPageNumber());
        assertEquals(10, countIterable(page.getItems()));
        assertEquals(10, page.getTotalPages());
        assertEquals(100, page.getTotalItems());

        // tests with 99 items, 10 items per page, testing start/middle/end
        page = controller.getPage(
                new PageImpl<>(
                        getXBeans(10),
                        PageRequest.of(0, 10),
                        99));
        assertEquals(10, page.getPageSize());
        assertEquals(0, page.getPageNumber());
        assertEquals(10, countIterable(page.getItems()));
        assertEquals(10, page.getTotalPages());
        assertEquals(99, page.getTotalItems());

        page = controller.getPage(
                new PageImpl<>(
                        getXBeans(10),
                        PageRequest.of(5, 10),
                        99));
        assertEquals(10, page.getPageSize());
        assertEquals(5, page.getPageNumber());
        assertEquals(10, countIterable(page.getItems()));
        assertEquals(10, page.getTotalPages());
        assertEquals(99, page.getTotalItems());

        page = controller.getPage(
                new PageImpl<>(
                        getXBeans(9),
                        PageRequest.of(9, 10),
                        99));
        assertEquals(10, page.getPageSize());
        assertEquals(9, page.getPageNumber());
        assertEquals(9, countIterable(page.getItems()));
        assertEquals(10, page.getTotalPages());
        assertEquals(99, page.getTotalItems());
    }

    @Test
    void testGetPageCustom() {
        TestController controller = new TestController();
        Page<Bean> page;

        // tests with 101 items, 10 items per page, testing start/middle/end
        page = controller.getPage(
                getXBeans(10),
                101,
                PageRequest.of(0, 10));
        assertEquals(10, page.getPageSize());
        assertEquals(0, page.getPageNumber());
        assertEquals(10, countIterable(page.getItems()));
        assertEquals(11, page.getTotalPages());
        assertEquals(101, page.getTotalItems());

        page = controller.getPage(
                getXBeans(10),
                101,
                PageRequest.of(5, 10));
        assertEquals(10, page.getPageSize());
        assertEquals(5, page.getPageNumber());
        assertEquals(10, countIterable(page.getItems()));
        assertEquals(11, page.getTotalPages());
        assertEquals(101, page.getTotalItems());

        page = controller.getPage(
                getXBeans(1),
                101,
                PageRequest.of(10, 10));
        assertEquals(10, page.getPageSize());
        assertEquals(10, page.getPageNumber());
        assertEquals(1, countIterable(page.getItems()));
        assertEquals(11, page.getTotalPages());
        assertEquals(101, page.getTotalItems());

        // tests with 100 items, 10 items per page, testing start/middle/end
        page = controller.getPage(
                getXBeans(10),
                100,
                PageRequest.of(0, 10));
        assertEquals(10, page.getPageSize());
        assertEquals(0, page.getPageNumber());
        assertEquals(10, countIterable(page.getItems()));
        assertEquals(10, page.getTotalPages());
        assertEquals(100, page.getTotalItems());

        page = controller.getPage(
                getXBeans(10),
                100,
                PageRequest.of(5, 10));
        assertEquals(10, page.getPageSize());
        assertEquals(5, page.getPageNumber());
        assertEquals(10, countIterable(page.getItems()));
        assertEquals(10, page.getTotalPages());
        assertEquals(100, page.getTotalItems());

        page = controller.getPage(
                getXBeans(10),
                100,
                PageRequest.of(9, 10));
        assertEquals(10, page.getPageSize());
        assertEquals(9, page.getPageNumber());
        assertEquals(10, countIterable(page.getItems()));
        assertEquals(10, page.getTotalPages());
        assertEquals(100, page.getTotalItems());

        // tests with 99 items, 10 items per page, testing start/middle/end
        page = controller.getPage(
                getXBeans(10),
                99,
                PageRequest.of(0, 10));
        assertEquals(10, page.getPageSize());
        assertEquals(0, page.getPageNumber());
        assertEquals(10, countIterable(page.getItems()));
        assertEquals(10, page.getTotalPages());
        assertEquals(99, page.getTotalItems());

        page = controller.getPage(
                getXBeans(10),
                99,
                PageRequest.of(5, 10));
        assertEquals(10, page.getPageSize());
        assertEquals(5, page.getPageNumber());
        assertEquals(10, countIterable(page.getItems()));
        assertEquals(10, page.getTotalPages());
        assertEquals(99, page.getTotalItems());

        page = controller.getPage(
                getXBeans(9),
                99,
                PageRequest.of(9, 10));
        assertEquals(10, page.getPageSize());
        assertEquals(9, page.getPageNumber());
        assertEquals(9, countIterable(page.getItems()));
        assertEquals(10, page.getTotalPages());
        assertEquals(99, page.getTotalItems());
    }
}