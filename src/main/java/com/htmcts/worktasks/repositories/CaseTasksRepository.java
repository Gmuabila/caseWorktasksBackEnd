package com.htmcts.worktasks.repositories;

import com.htmcts.worktasks.domaine.CaseTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseTasksRepository extends JpaRepository<CaseTask, Integer> {
}
