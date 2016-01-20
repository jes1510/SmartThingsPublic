/**
 *  Arduino Garage Door
 *
 *  Copyright 2016 JESSE
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
metadata {
	definition (name: "Arduino Garage Door Opener", author: "Jes1510") {
		capability "Garage Door Control"
		capability "Contact Sensor"
		capability "Refresh"
		capability "Sensor"
		
	}
	
	tiles {
		standardTile("toggle", "device.door", width: 2, height: 2) {
			state("unknown", label:'${name}', action:"refresh.refresh", icon:"st.doors.garage.garage-open", backgroundColor:"#ffa81e")
			state("closed", label:'${name}', action:"door control.open", icon:"st.doors.garage.garage-closed", backgroundColor:"#79b821", nextState:"opening")
			state("open", label:'${name}', action:"door control.close", icon:"st.doors.garage.garage-open", backgroundColor:"#ffa81e", nextState:"closing")
			state("opening", label:'${name}', icon:"st.doors.garage.garage-opening", backgroundColor:"#ffe71e")
			state("closing", label:'${name}', icon:"st.doors.garage.garage-closing", backgroundColor:"#ffe71e")
			
		}
		standardTile("open", "device.door", inactiveLabel: false, decoration: "flat") {
			state "default", label:'open', action:"door control.open", icon:"st.doors.garage.garage-opening"
		}
		standardTile("close", "device.door", inactiveLabel: false, decoration: "flat") {
			state "default", label:'close', action:"door control.close", icon:"st.doors.garage.garage-closing"
		}
		standardTile("refresh", "device.door", inactiveLabel: false, decoration: "flat") {
			state "default", label:'', action:"refresh.refresh", icon:"st.secondary.refresh"
		}

		main "toggle"
		details(["toggle", "open", "close", "refresh"])
	}
}


// Parse incoming device messages to generate events
def parse(String description) {
	def value = zigbee.parse(description)?.text
	def name = value in ["open","closed", "opening", "closing"]  ? "device.door" : null
	def result = createEvent(name: name, value: value)
	log.debug "Parse returned ${result?.descriptionText}"
	return result
}


// handle commands
def open() {
	log.debug "Executing 'open'"
	zigbee.smartShield(text: "open").format()
}

def close() {
	log.debug "Executing 'close'"
	zigbee.smartShield(text: "close").format()
}

def refresh() {
 log.debug("Executing 'refresh'")
 zigbee.smartShield(text: "query").format()
 }

def handlerName(evt) {
    log.debug "Is this event a state change? ${evt.isStateChange()}"
}
    
def closing() {
	log.debug("Closinating")
}