package com.store.customer.repository;

import com.store.customer.entity.LineItem;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LineItemRepository extends JpaRepository<LineItem, UUID> {
}
