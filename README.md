### Методы
**Метод getInode()**<br>
Следует использовать данный метод крайне осторожно. Отладка метода делалась для комбинации linux + java 8(компиляция) + java 8(выполнение).
На windows метод не работает, поскольку inode на windows не существует.
При использовании не 8-й java у клиента, наблюдались проблемы с отработкой рефлексии.
<p>
На Windows отдает -1.<br>
На RHEL 7.8 + java 8 отдает inode.<br>
На Debian 12.1 + java 17 отдает ошибку рефлексии:<br>
<i>Exception in thread "main" java.lang.reflect.InaccessibleObjectException: Unable to make field
private final long sun.nio.fs.UnixFileKey.st_ino accessible: module java.base does not "opens sun.nio.fs" 
to unnamed module.</i>
</p>
