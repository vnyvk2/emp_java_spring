package com.example.emp.repository;

import com.example.emp.model.Employee;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryEmployeeRepository {
    private final Map<Long, Employee> store = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    public synchronized Employee save(Employee e) {
        if (e.getId() == null) {
            e.setId(idCounter.incrementAndGet());
        }
        store.put(e.getId(), e);
        return e;
    }

    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Employee> findAll() {
        return new ArrayList<>(store.values());
    }

    public synchronized void deleteById(Long id) {
        store.remove(id);
    }
}