import {RemoteApp} from '@eclipse-scout/core';
import * as testipa from './index';

Object.assign({}, testipa); // Use import so that it is not marked as unused

new RemoteApp().init();
