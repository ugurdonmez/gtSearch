var MyObject  = {
    processDOMContentLoaded: function(doc) {
        
		var scrip = doc.createElement('script');
        scrip.src = 'https://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js';
		doc.getElementsByTagName('head')[0].appendChild(scrip);
		
		
		var script = doc.createElement('script');
        script.src = 'change.js';
        script.type = 'text/javascript';		
        doc.getElementsByTagName('head')[0].appendChild(script);
		
		
    }
};

window.addEventListener('load', function() {
    var appcontent = document.getElementById('appcontent');
    if(appcontent != null) {
        appcontent.addEventListener('DOMContentLoaded', function(event) {
            var doc = event.originalTarget;
            if(doc instanceof HTMLDocument) {
                MyObject.processDOMContentLoaded(doc);
            }
        }, true);
    }
}, false);
