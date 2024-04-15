package com.example.blog.component;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Component
public class RequestCounter {
  private final AtomicInteger counter = new AtomicInteger(0);

  public synchronized int incrementAndGet() {
    return counter.incrementAndGet();
  }
}
