<h3 style="text-align: center; line-height: 4vh;">
Міністерство Освіти і Науки України<br>
Тернопільський національний технічний університет імені Івана Пулюя<br>
Факультет комп’ютерно-інформаційних систем та програмної інженерії</h3>

<br>
<h3 style="text-align: right;">
<i>Кафедра "Програмної інженерії"</i></h3>

<br><br><br><br>

<h3 style="text-align: center; line-height: 4vh;">
ЗВІТ<br>
з лабораторної роботи №2<br>
з навчальної дисципліни "Конструювання програмного забезпечення"<br>
Тема: "Midi-piano"</h3>

<br><br><br><br>

<h3 style="text-align: right; line-height: 4vh;">
Підготувала:<br>
студентка групи СП-32<br>
Прачук Маргарита Вікторівна</h3>

<br><br><br>
<h3 style="text-align: center;">Тернопіль 2024</h3>
<br><br><br>

## Мета
Метою цієї роботи є закріплення знання з написання специфікацій та використання кінцевих автоматів для розробки програмного забезпечення

## Опис ПЗ
Відповідно до завдання було виконані наступні завдання та вимоги:
- погравати музичні ноти за допомогою клавіш на клавіатурі комп'ютера;
- імітувати різноманітні інструменти;
- програвати ноти в різних октавах;
- записувати послідовності нот та потім їх відтворювати.

### Вимога A
Міді-синтезатор повинен дозволяти погравати деякий набір нот (pitches) {C, C #, D, ...# B} 
за допомогою клавіш {'1 ', '2', ... '-', '='}. Відповідно, коли одна з цих клавіш
натиснута, повинна зазвучати нота, якщо вона ще не звучить до цього, те ж саме, при
відпусканні клавіші, нота повинна перестати звучати, якщо вона звучала до цього. Для
реалізації цього функціоналу, створені сигнатури відповідних методів
PianoMachine.beginNote і PianoMachine.endNote, які будуть викликатися відповідно при
натисненні та при відпусканні клавіші. Використовуйте ці методи в якості точок входу у
вашій реалізації.

_Завдання:_
1. Написати специфікації для методів PianoMachine.beginNote і PianoMachine.endNote, а
   також для усіх допоміжних методів, які будуть використовуватися.
2. Написати тести для перевірки роботи цих методів
3. Реалізувати необхідний функціонал методів PianoMachine.beginNote і
   PianoMachine.endNote та необхідних допоміжних методів. В якості відправної точки,
   зроблена попередня реалізація цих методів, яка завжди програє ноту ‘C’.

### Вимога B
   Midi-синтезатор повинен уміти переключати midi-інструменти. Клавіша ‘І’ повинна
   виконувати переключення midi-інструмента на наступний в переліку доступних або
   починати з першого, якщо перелік вичерпано.
   В наданій реалізації аплету, клавіша ‘I’ прив’язана до методу
   PianoMachine.changeInstrument, в якому можна реалізовувати необхідний функціонал
   
_Завдання:_
1. Написати специфікація для методу changeInstrument та для усіх допоміжних методів
2. Написати тести для цих методів
3. Додати новий стан до автомату PianoMachine, що відображатиме режим інструменту,
   вибрного в методі changeInstrument і відповідним чином змінити інші методи, який це
   торкатиметься. Перелік інструментів знаходиться в enum Instrument пакету midi.

### Вимога С
   Натиснення клавіш ‘C’ і ‘V’ повинне зсувати поточну ноту відповідно вверх або вниз на
   одну октаву (12 півтонів). Повинна бути можливість зсуву на дві октави в будь-якому
   напрямку від початкової ноти. Відповідні клавіші викликають методи PianoMachine.shiftUp і
   PianoMachine.shiftDown.

_Завдання:_ 
1. Написати специфікація для методів PianoMachine.shiftUp і PianoMachine.shiftDown та
   для усіх допоміжних методів
2. Написати тести для цих методів
3. Реалізувати методи shiftUp і shiftDown та допоміжні методи

### Вимога D
   Синтезатор повинен мати можливість записувати (Recording) та відтворювати(Playback)
   послідовності нот, зберігаючи ритм, з яким вони програвалися. Новий запис повинен
   замінювати попередній. Клавіша 'R' вмикає і вимикається режим запису, а клавіша 'P' -
   викликає відтворення. Створено сигнатури методів PianoMachine.toggleRecording і
   PianoMachine.playback.
   
_Завдання:_
1. Написати специфікація для методів toggleRecording і playback та для усіх допоміжних
   методів
2. Написати тести для цих методів
3. Реалізувати методи toggleRecording і playback та допоміжні методи

### Вимога E
   Виправте проблему повязану з програванням нот, коли синтезатр знаходиться в режимі
   відтворення. Проблема полягає в тому, що ноти програються всі разом по завершенні
   відтворення.
   Тобто, введення з клавіатури, що відбувається під час відтворення повинно не мати
   ніякого ефекту, або програватися негайно. (Може бути корисним метод
   KeyEvent.getWhen()).

Взаємодія користувача з ПЗ відбувається через графічний інтерфейс, який дозволяє виконувати команди та взаємодіяти з додатком за допомогою клавіатури.

## Основні можливості ПЗ
- програвання музичних нот за допомогою клавіш комп'ютера ('1', '2', ... '-', '=');
- імітація різноманітних музичних інстументів, поточний інструмен графічно відображається у верхньому лівому куті (зміна інструменту відбувається за допомогою клавіші 'I');
- програвання нот в різних октавах (клавіша 'С' зсуває поточну ноту на 12 півтонів вверх, а клавіша 'V' - на 12 півтонів вниз);
- запис та відтворення послідовності нот (клавіша 'R' запускає та зупиняє процес запису, клавіша 'P' - відтворює мелодію).

Midi Рiano виглядає наступним чином:

![img](https://i.imgur.com/evOsG1H.jpeg)

## Імплементація
Код програми знаходиться в папці [src](/src). Виконання проводилось в середовищі **JetBrains IntelliJ IDEA**, з використаням **Gradle**. Для корректної роботи програми необхідна 21 версія JDK.

Клас `PianoMachine` є основним у програмі, він керує обробкою та відтворенням нот. Для взаємодії з користувачем розроблений клас `PianoApp`, 
який створює графічний інтерфейс. Звукове відтворення здійснюється класом `Midi`.

Базовими методами є `beginNote`, що слугує для початку ноти та `endNote` - для закінчення ноти.
Зміна музичного інструменту відбувається за допомогою функції `changeInstrument`.
Програвання нот у різних октавах організовано методами `shiftUp` та `shiftDown`. 
Метод `toggleRecording` записує послідовність нот, тобто мелодію, а `playback` відтворює її.


Для всіх методів було написано специфікації, що облегшує розуміння їх функціональності та використання, а також сприяє підтримці коду та його подальшому розвитку.


## Тестування
У процесі тестування використовувався фреймворк JUnit 5. Були розроблені тести для всіх методів, які були вказані в завданні, а саме:

- `singleNoteTest` тест для відтворення(програвання) однієї ноти;
- `changeInstrumentTest` тест для зміни музичного інстументу;
- `shiftUpTest` тест для зміни октави вверх;
- `shiftDownTest` тест для зміни октави вниз;
- `shiftDownAndUpTest` тест для послідовної зміни октави вгору і потім вниз;
- `recordingAndPlaybackTest` тест для запису мелодії та її відтворення.

Тести на `CI github` не проходять через винятки типу javax.sound.midi.MidiUnavailableException та
java.lang.IllegalArgumentException, які виникають у тестах. Може бути проблема з доступом до MIDI ресурсів.
Щоб вирішити ці проблеми, потрібно перевірити налаштування CI середовища та переконатися, що
MIDI ресурси доступні, або виконати додатковий аналіз, щоб з'ясувати, чому MIDI недоступний для ваших тестів.

## Завдання на захист

1. Для реалізації логування в файл .log активності користувача можна скористатися бібліотекою логування, наприклад, Log4j або SLF4J. 
Детальніше розглянемо як це можна зробити з використанням Log4j з системою збирання Gradle:
- додати залежності до файлу `build.gradle`
```
dependencies {
  implementation 'org.apache.logging.log4j:log4j-api:2.x.x'
  implementation 'org.apache.logging.log4j:log4j-core:2.x.x'
  }
```
вказати потрібну версію Log4j в рядку 2.x.x (де x - номер версії). 
- cтворити файл конфігурації Log4j. Наприклад, log4j.properties або log4j2.xml.
```
# Налаштування рівня логування
log4j.rootLogger=INFO, file

# Налаштування виводу в файл
log4j.appender.file=org.apache.log4j.RollingFileAppender
log4j.appender.file.File=application.log
log4j.appender.file.MaxFileSize=10MB
log4j.appender.file.MaxBackupIndex=5
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n

```
- проект готовий для використання Log4j, можна створити логгери у коді та здійснювати логування так само, як описано нижче:
```
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class YourClass {
    private static final Logger logger = LogManager.getLogger(YourClass.class);

    public void yourMethod() {
        // Використання логера для запису повідомлень
        logger.info("Це інформаційне повідомлення.");
        logger.error("Це повідомлення про помилку.");
    }
}
```

2. Оновлений UI із текстовим описом функціоанальних клавіш. Запис, відтворення і т.д.
![img](https://i.imgur.com/VYq9cmd.jpeg)

3. Серіалізація даних для цього проекту може включати опис методу збереження та відновлення стану фортепіано та дій 
користувача. Формат файлу для збереження даних для прикладу можна розглянути текстовий формату з використанням JSON.
При читанні такого файлу можуть виникнути наступні складнощі:
- Розбір JSON: Потрібно коректно розбирати JSON-рядок з файлу, перевіряти на наявність помилок та відновлювати дані.
- Перевірка правильності даних: Потрібно перевіряти, що дані, зчитані з файлу, є коректними та відповідають очікуваному формату.
- Відновлення стану програми: Після зчитування даних з файлу програма повинна відновити стан фортепіано та історію дій користувача до їх попереднього стану.
- Обробка помилок читання файлу: Потрібно враховувати можливі помилки, такі як відсутність файлу, невірний формат файлу тощо, і обробляти їх належним чином.






