/*
Copyright 2008 Flaptor (flaptor.com) 

Licensed under the Apache License, Version 2.0 (the "License"); 
you may not use this file except in compliance with the License. 
You may obtain a copy of the License at 

    http://www.apache.org/licenses/LICENSE-2.0 

Unless required by applicable law or agreed to in writing, software 
distributed under the License is distributed on an "AS IS" BASIS, 
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
See the License for the specific language governing permissions and 
limitations under the License.
*/
package com.flaptor.hounder.crawler.modules.boost;

import com.flaptor.hounder.crawler.modules.FetchDocument;
import com.flaptor.util.Config;
import com.flaptor.util.Execute;

/**
 * @author Flaptor Development Team
 */
public abstract class ABoostCondition extends ABoostModule  {

    public static enum Type {UrlPattern, Keyword , UrlPatternAndKeyword};

    public ABoostCondition(Config config) {
        super(config);
    }


    public static ABoostCondition getBoostCondition (Config config, Type type) {
        ABoostCondition cond = null;
        switch (type) {
            case UrlPattern:
                cond = new UrlPatternBoostCondition(config);
                break;
            case Keyword:
                cond = new KeywordBoostCondition(config);
                break;
            case UrlPatternAndKeyword:
                cond = new CompositeBoostCondition(new UrlPatternBoostCondition(config), new KeywordBoostCondition(config));
                break;
        }
        if (null == cond) {
            throw new IllegalArgumentException("Unknown boost condition: "+type);
        }

        return cond;
    }
    
    public abstract boolean eval (FetchDocument doc);
    public boolean hasValue(FetchDocument doc) {return false;}
    public double getValue (FetchDocument doc) {return 0;}
    public abstract void close();

} 
