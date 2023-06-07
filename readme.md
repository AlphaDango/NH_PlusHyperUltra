# NHPlus

##Informationen zur Lernsituation
Du bist Mitarbeiter der HiTec GmbH, die seit über 15 Jahren IT-Dienstleister und seit einigen Jahren ISO/IEC 27001 zertifiziert ist. Die HiTec GmbH ist ein mittelgroßes IT-Systemhaus und ist auf dem IT-Markt mit folgenden Dienstleistungen und Produkten vetreten: 

Entwicklung: Erstellung eigener Softwareprodukte

Consulting: Anwenderberatung und Schulungen zu neuen IT- und Kommunikationstechnologien , Applikationen und IT-Sicherheit

IT-Systembereich: Lieferung und Verkauf einzelner IT-Komponenten bis zur Planung und Installation komplexer Netzwerke und Dienste

Support und Wartung: Betreuung von einfachen und vernetzten IT-Systemen (Hard- und Software)

Für jede Dienstleistung gibt es Abteilungen mit spezialisierten Mitarbeitern. Jede Abteilung hat einen Abteilungs- bzw. Projektleiter, der wiederum eng mit den anderen Abteilungsleitern zusammenarbeitet.

 

##Projektumfeld und Projektdefinition

Du arbeitest als Softwareentwickler in der Entwicklungsabteilung. Aktuell bist du dem Team zugeordnet, das das Projekt "NHPlus" betreut. Dessen Auftraggeber - das Betreuungs- und Pflegeheim "Curanum Schwachhausen" - ist ein Pflegeheim im Bremer Stadteil Schwachhausen - bietet seinen in eigenen Zimmern untergebrachten Bewohnern umfangreiche Therapie- und Serviceleistungen an, damit diese so lange wie möglich selbstbestimmt und unabhängig im Pflegeheim wohnen können. Curanum Schwachhausen hat bei der HiTec GmbH eine Individualsoftware zur Verwaltung der Patienten und den an ihnen durchgeführten Behandlungen in Auftrag gegeben. Aktuell werden die Behandlungen direkt nach ihrer Durchführung durch die entsprechende Pflegekraft handschriftlich auf einem Vordruck erfasst und in einem Monatsordner abgelegt. Diese Vorgehensweise führt dazu, dass Auswertungen wie z.B. welche Behandlungen ein Patient erhalten oder welche Pflegkraft eine bestimmte Behandlung durchgeführt hat, einen hohen Arbeitsaufwand nach sich ziehen. Durch NHPlus soll die Verwaltung der Patienten und ihrer Behandlungen elektronisch abgebildet und auf diese Weise vereinfacht werden.

Bei den bisher stattgefundenen Meetings mit dem Kunden konnten folgende Anforderungen an NHPlus identifiziert werden:

- alle Patienten sollen mit ihrem vollen Namen, Geburtstag, Pflegegrad, dem Raum, in dem sie im Heim untergebracht sind, sowie ihrem Vermögensstand erfasst werden.

- Die Pflegekräfte werden mit ihrem vollen Namen und ihrer Telefonnumer erfasst, um sie auf Station schnell erreichen zu können.

- jede Pflegekraft erfasst eine Behandlung elektronisch, indem sie den Patienten, das Datum, den Beginn, das Ende, die Behandlungsart sowie einen längeren Text zur Behandlung erfasst.

- Die Software muss den Anforderungen des Datenschutzes entsprechen. 

- Die Software ist zunächst als Desktopanwendung zu entwickeln, da die Pflegekräfte ihre Behandlungen an einem stationären Rechner in ihrem Aufenthaltsraum erfassen sollen.

 

Da in der Entwicklungsabteilung der HiTech GmbH agile Vorgehensweisen vorgeschrieben sind, wurde für NHPlus Scum als Vorgehensweise gewählt.

 

##Stand des Projektes

In den bisherigen Sprints wurden die Module zur Erfassung der Patienten- und Behandlungsdaten fertiggestellt. Es fehlt das Modul zur Erfassung der Pflegekräfte. Deswegen kann bisher ebenfalls nicht erfasst werden, welche Pflegekraft eine bestimmte Behandlung durchgeführt hat. In der letzten Sprint Review sind von der Curanum Schwachhausen Zweifel angebracht worden, dass die bisher entwickelte Software den Anforderungen des Datenschutzes genügt.

## Technische Hinweise

Wird das Open JDK verwendet, werden JavaFX-Abhängigkeiten nicht importiert. Die Lösung besteht in der Installation der neuesten JDK-Version der Firma Oracle.

## Technische Hinweise zur Datenbank

- Benutzername: SA
- Passwort: SA
- Bitte nicht in die Datenbank schauen, während die Applikation läuft. Das sorgt leider für einen Lock, der erst wieder verschwindet, wenn IntelliJ neugestartet wird!

--------------------------------------------------------------------------------------------------------------

# Durchgeführte Anpassungen 

Die durchgeführten Anpassung an dem bisherigen Code orientieren sich Grundlegend an den Anforderungskriterien der gegebenen Musterstories. Einige zusätzliche Funktionen, welche wir während der Entwicklungsphase als sinnvoll erachtet haben, wurden ebenfalls dem Programm hinzugefügt.
Die Auswertung der zu bestehenden Testfälle der Userstories sind nachfolgend aufgelistet.

## US Pflegermodul
Alle geforderten Anforderungskriterien wurden erfolgreich implementiert. 
Lediglich die Nachverfolgung der Änderungen, geschieht nicht über zusätzliche Einträge in der Datenbank, sondern über eine für jeden Typ individuell erstellt Log-Datei.

### Testfälle
TF_1: Alle Pflegekräfte anzeigen
-Vorbedingung: Der Nutzer/ die Nutzerin hat sich angemeldet und in der Navigationsleiste “Pflegekräfte” ausgewählt
-auszuführende Testschritte: 
1. Einloggen als Bereichsleiter/Supervisor
2. Bereich Pflegekräfte auswählen  <br>
-erwartetes Ergebnis: Es werden alle Pflegekräfte,die in der Datenbankgespeichert sind, in einer tabellarischen Ansicht mit Vor- und Nachnamen und Telefonnummer und ihrer Rolle angezeigt.
- **Ergebnis:** Pflegekräfte werden Bereichsleitern mit allen relevanten Daten angezeigt.

TF_2: neue Pflegekraft hinzufügen
-Vorbedingung: Der Nutzer/ die Nutzerin hat sichangemeldet und in derNavigationsleiste “Pflegekräfte” ausgewählt
-auszuführende Testschritte:
1.Nutzer/in hat alle vorgesehenen Felder (Vor- und Nachname,Telefonnummer, Rolle) ausgefüllt
2.Nutzer/in hat den Button “hinzufügen” gedrückt <br>
-erwartetes Ergebnis: Der Tabelle wird die Pflegekraft mit ihren Daten und einer automatisch erstellten ID hinzugefügt und auch in der Datenbank gespeichert.
- **Ergebnis:** Bei Eingabe aller erforderlichen Daten wird ein neuer Pfleger mit einer automatisch erstellten ID hinzugefügt und in der Datenbank gespeichert. Eingaben werden validiert und erst bei korrekter Belegung aller Felder wird eine neue Pflegekraft hinzugefügt. Beim Hinzufügen eines neuen Pflegers wird ebenfalls ein neuer User / Passwort für den Login und ihre Rolle generiert.

TF_3: Pflegekraft-Daten ändern
-Vorbedingung: Der Nutzer/ die Nutzerin hat auf derNavigationsleiste“Pflegekräfte” ausgewählt
-auszuführende Testschritte:
1.Nutzer/in wählt mit Doppelklick den zu ändernden Wert einer Pflegekraftaus
2.Nutzer/in ändert den Wert
3.Nutzer/in beendet die Eingabe mit der Entertaste  <br>
-erwartetes Ergebnis: Der geänderte Wert wird in der Tabelle angezeigt und zusätzlich in der Datenbank gespeichert
- **Ergebnis:** Der geänderte Wert wird in der Tabelle angezeigt und in der Datenbank gespeichert

TF_4: Pflegekraft-Daten löschen (sperren)
-Vorbedingung: Der Nutzer/ die Nutzerin hat auf derNavigationsleiste“Pflegekräfte” ausgewählt
-auszuführende Testschritte:
1.Nutzer/in wählt die zu löschenden (sperrenden) Personen aus
2.Nutzer/in drückt den “Löschen/Archivieren” (Sperren) Button  <br>
-erwartetes Ergebnis: Die gelöschte bzw. gesperrte Pflegekraft wird nicht mehrin der Tabelle angezeigt, die Daten bleiben aber in der Datenbank (Speicherfrist), bis die Frist abgelaufen ist.
- **Ergebnis:** Die gelöschte Pflegekraft wird nicht mehr in der Tabelle angezeigt und mit einem DATEOFARCHIVE in der Datenbank gespeichert. Nach ablauf der Speicherfrist von 10 Jahren, wird der Wert beim Aufruf der gesamten Daten entfernt.

## Speicherfrist

### Testfälle

TF_1: Patient archivieren
-Vorbedingung: Der User hat einen Patienten ausgewählt.
-auszuführende Testschritte: Den Button zum Archivierenbetätigen
-erwartetes Ergebnis: Die View aktualisiert sich und der betroffene Patient wird nicht mehr angezeigt.
- **Ergebnis:** Wenn der Button zum archivieren gedrückt wird, wird die aktuelle Zeit dem Datensatz als Timestamp hinzugefügt und der Datensatz verschwindet aus der sichtbaren Tabelle.

TF_2: Datenbank enthält Datensatz
-Vorbedingung: Der User hat Zugriff auf Datenbank.
-auszuführende Testschritte: Datenbank nach archiviertem Datensatz durchsuchen.
-erwartetes Ergebnis: Datensatz ist noch vorhanden und besitzt ein“gelöscht”-Flag und/oder einen Timestamp
- **Ergebnis:** Der Datensatz ist in der Datenbank mit dem Timestamp vorhanden. Eine Einsicht der Daten ist nur über das Script möglich.

TF_3: Automatische Löschung
-Vorbedingung: Der User hat Zugriff auf Datenbank.
-auszuführende Testschritte: Timestamp wird auf “vor9 Jahren und 364 Tagen,23 Stundne und 55 Minuten” gesetzt. Nach 5 Minuten wird die Datenbank nachdem zuvor archivierten Datensatz durchsucht.-erwartetes Ergebnis: Datensatz ist nicht mehr vorhanden.
- **Ergebnis:** Die Daten werden automatisch gelöscht.

## Login
Beim Login der Benutzer, zur Verwendung der Anwendung, wird zwischen Pfleger und Bereichsleiter/Supervisor unterschieden

### Testfälle

TF_1: Login mit falschen Benutzerdaten
-Vorbedingung: Das Programm muss gestartet werden
-Auszuführende Testschritte: Das Einloggen mit falschen Daten
-Erwartetes Ergebnis: Fehlermeldung, den Zugriff auf das Programm verweigern 
- **Ergebnis:** Der Zugriff auf die Anwendung wird verweigert und es erscheint eine Fehlermeldung

TF_2: Login mit richtigen Benutzerdaten
-Vorbedingung: Das Programm muss gestartet werden
-Auszuführende Testschritte: Das Einloggen mit validen Daten
-Erwartetes Ergebnis: Erfolgreiche Anmeldung
- **Ergebnis:** Der angemeldete Benutzer wird auf das Hauptformular weitergeleitet.

TF_3: Benutzerrollen-Test
-Vorbedingung: Es muss eine Benutzer eingeloggt sein
-Auszuführende Testschritte: Sichtung der Tabellen
-Erwartetes Ergebnis: Nur für den Benutzer relevante Daten anzeigen
- **Ergebnis:** Jeder Nutzer erhält anhand seiner Rolle und zugewiesenen Patienten zugriff auf diese Werte. 
Bereichsleiter erhalten Zugriff auf die Verwaltung von Pflegekräften. Pfleger können nur die ihnen zugewiesenen Patientendaten und Behandlungen einsehen.

TF_4: Löschung und Bearbeitung von Daten als Administrator
-Vorbedingung: Der Benutzer muss als Administrator eingeloggt sein
-Auszuführende Testschritte: “Löschung” und Bearbeitung von Daten
-Erwartetes Ergebnis: Erfolgreiche Löschung & Bearbeitung von Daten
- **Ergebnis:** Pflegerdaten können nur von Bereichsleitern bearbeitet werden. Patienten und Behandlungsdaten können auch von Pflegern bearbeitet werden, solange diese ihnen zugeordnet sind.

## Entfernen Vermögensstand
### Testfälle
TF_1: Alle Views aufrufen
-Vorbedingung:User hat sich angemeldet.
-auszuführende Testschritte: Nacheinander jede View aufrufen.
-erwartetes Ergebnis: Es befindet sich nirgendwo ein Feld “Vermögensstand”.
- **Ergebnis:** Kein View enthält einen Tabelleneintrag mit einem Vermögensstand.

TF_2: Datenbank überprüfen
-Vorbedingung:User hat Zugang zu allen Datenbankfeldern(als Admin eingeloggt)
-auszuführende Testschritte: Tabelle auf “Vermögensstand” hin überprüfen
-erwartetes Ergebnis:Es befindet sich nirgendwo ein Feld “Vermögensstand”.
- **Ergebnis:** Validierung kann nur über das DB-Script vorgenommen werden. Das Feld "Vermögensstand" wurde vollständig aus der Datenbank entfernt.

## Zusätzliche Inforamtionen
Benutzer und Passwörter werden beim hinzufügen einer neuen Pflegekraft automatisch generiert.
Jeder Benutzer kann seinen Usernamen, Telefonnummer sowie Passwort im Benutzermenü oben links ändern.
Die generierten Logindaten setzen sich wie folgt zusammen mit diesem Beispiel:

Vorname:  Max
Nachname: Mustermann
Tel.Nr.:  04209665238

Daraus folgt der Username und das Passwort max.m38


## Benutzername und Passwort für das Login
### Login als Supervisor: 
- Benutzername: Dango
- Passwort: test

### Login als Pfleger:
- Benutzername: Axle
- Passwort: test
