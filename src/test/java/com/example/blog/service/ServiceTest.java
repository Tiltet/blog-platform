package com.example.blog.service;

import com.example.blog.services.Service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ServiceTest {
    @Test
    void sum() {
        Service service = new Service();
        int sum = service.sum(10, 12);
        Assertions.assertEquals(22, sum);
    }
}
