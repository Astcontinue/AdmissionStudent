# ------------------------------------------------------------
# student Trigger
# ------------------------------------------------------------

-- 当student表中有数据插入时，student_grades表中对应的字段同时更新
CREATE TRIGGER update_student_grade
AFTER INSERT
ON student
FOR EACH ROW
BEGIN
   INSERT INTO student_grades(student_id, name, level) VALUES (NEW.id, NEW.name, NEW.level);
END;


-- 当student表中的数据被删除时，删除student_grades表中对应的记录
CREATE TRIGGER delete_student_grade
AFTER DELETE
ON student
FOR EACH ROW
BEGIN
   DELETE FROM student_grades WHERE student_id = OLD.id;
END;

-- 当student表中的数据被更新时，更新student_grades表中对应的记录
CREATE TRIGGER update_student_grade
AFTER UPDATE
ON student
FOR EACH ROW
BEGIN
   UPDATE student_grades
   SET name = NEW.name, level = NEW.level
   WHERE student_id = NEW.id;
END;

