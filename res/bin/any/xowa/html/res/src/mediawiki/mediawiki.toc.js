( function ( mw, $ ) {
	'use strict';

	// Table of contents toggle
  mw.hook('wikipage.content').add(function($content) {
      $content.find('.toc').addBack('.toc').each(function() {
          var hideToc, $this = $(this), $tocToggleCheckbox = $this.children('.toctogglecheckbox'), $tocTitle = $this.find('.toctitle'), $tocToggleLink = $this.find('.togglelink'), $tocList = $this.find('ul').eq(0);
          function toggleToc() {
              if ($tocList.is(':hidden')) {
                  $tocList.slideDown('fast');
                  $tocToggleLink.text(mw.msg('hidetoc'));
                  $this.removeClass('tochidden');
                  mw.cookie.set('hidetoc', null);
              } else {
                  $tocList.slideUp('fast');
                  $tocToggleLink.text(mw.msg('showtoc'));
                  $this.addClass('tochidden');
                  mw.cookie.set('hidetoc', '1');
              }
          }
          if (!$tocToggleCheckbox.length && $tocTitle.length && $tocList.length && !$tocToggleLink.length) {
              hideToc = mw.cookie.get('hidetoc') === '1';
              $tocToggleLink = $('<a>').attr({
                  role: 'button',
                  tabindex: 0
              }).addClass('togglelink').text(mw.msg(hideToc ? 'showtoc' : 'hidetoc')).on('click keypress', function(e) {
                  if (e.type === 'click' || e.type === 'keypress' && e.which === 13) {
                      toggleToc();
                  }
              });
              $tocTitle.append($tocToggleLink.wrap('<span class="toctoggle"></span>').parent().prepend('&nbsp;[').append(']&nbsp;'));
              if (hideToc) {
                  $tocList.hide();
                  $this.addClass('tochidden');
              }
          }
      });
  });

}( mediaWiki, jQuery ) );
