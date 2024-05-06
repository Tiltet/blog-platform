package com.example.blog.component;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@Scope("singleton")
public class RequestCounter {
  private final AtomicInteger counter = new AtomicInteger(0);

  public synchronized int increment() {
     return counter.incrementAndGet();
  }
}
