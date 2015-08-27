var gulp = require('gulp'),
    gutil = require('gulp-util'),
    concat = require('gulp-concat'),
    gulpif = require('gulp-if'),
    uglify = require('gulp-uglify'),
    minifyHTML = require('gulp-minify-html'),
    connect = require('gulp-connect'),
    minifyCSS = require('gulp-minify-css')
    less = require('gulp-less'),
    path = require('path'),
    jshint = require('gulp-jshint');

var env,
    jsSources3rd,
    jsSources,
    cssSources3rd,
    cssSources,
    lessSources,
    watchLessSources,
    htmlSources,
    partialSources,
    fontSources,
    imageSources,
    outputDir,
    jsExt,
    cssExt;

env = process.env.NODE_ENV || 'development';

// determine whether running in DEV or PRODUCTION mode
if(isDevelopment()) {
    outputDir = 'app/builds/development/';
    jsExt = '.js';
    cssExt = '.css';
} else if(isProduction()) {
    outputDir = 'app/builds/production/';
    jsExt = '.min.js';
    cssExt = '.min.css';
} else {
    throw new Error('Please specify NODE_ENV as "production".');
}

// all 3rd party js dependencies defined here
jsSources3rd = [
    'app/bower_components/jquery/dist/jquery' + jsExt,
    'app/bower_components/bootstrap/dist/js/bootstrap' + jsExt,
    'app/bower_components/lodash/lodash' + jsExt,
    'app/bower_components/angular/angular' + jsExt,
    'app/bower_components/angular-route/angular-route' + jsExt,
    'app/bower_components/angular-loading-bar/build/loading-bar' + jsExt,
    'app/bower_components/angular-animate/angular-animate' + jsExt,
    'app/bower_components/angular-bootstrap/ui-bootstrap-tpls' + jsExt,
    'app/bower_components/elasticsearch/elasticsearch.angular' + jsExt,
    'app/bower_components/moment/moment' + jsExt,
    'app/bower_components/angular-moment/angular-moment' + jsExt,
    'app/bower_components/angular-smart-table/dist/smart-table' + jsExt,
    'app/bower_components/d3/d3' + jsExt,
    'app/bower_components/nvd3/build/nv.d3' + jsExt,
    'app/bower_components/angular-nvd3/dist/angular-nvd3' + jsExt,
    'app/bower_components/angular-multi-select/isteven-multi-select' + '.js'
];

// all application js dependencies defined here
jsSources = [
    'app/components/scripts/app.js',
    'app/components/scripts/config.js',
    'app/components/scripts/controllers/**/*.js',
    'app/components/scripts/services/**/*.js',
    'app/components/scripts/filters/**/*.js',
    'app/components/scripts/directives/**/*.js'
];

// all application less dependencies defined here
lessSources = [
    'app/components/less/bootstrap.less'
];

// only watch the application less dependencies
watchLessSources = [
    'app/components/less/**/*.less'
];

// all 3rd party css dependencies defined here
cssSources3rd = [
    'app/bower_components/bootstrap/css/bootstrap-theme' + cssExt,
    'app/bower_components/font-awesome/css/font-awesome' + cssExt,
    'app/bower_components/angular-loading-bar/build/loading-bar' + cssExt,
    'app/bower_components/nvd3/build/nv.d3' + cssExt,
    'app/bower_components/angular-multi-select/isteven-multi-select' + '.css'
];

// all application css dependencies defined here
cssSources = [
    'app/components/css/*.css'
];

// all font dependencies defined here, including the 3rd's and application's
fontSources = [
    'app/bower_components/bootstrap/dist/fonts/*.*',
    'app/bower_components/font-awesome/font/*.*',
    'app/components/fonts/*.*'
];

// all images defined here
// TODO: add image compression if needed
imageSources = [
    'app/components/images/**/*.*'
];

// all html partials and pages defined here
htmlSources = [
    'app/components/html/**/*.html'
];

partialSources = [
    'app/components/partials/**/*.html'
];

gulp.task('lint', function() {
    return gulp.src(jsSources)
        .pipe(jshint())
        .pipe(jshint.reporter('default'));
});

gulp.task('js', ['lint'], function() {
    // handle 3rd scripts
    if(jsSources3rd.length > 0) {
        gulp.src(jsSources3rd)
            .pipe(concat('lib.js'))
            .pipe(gulp.dest(outputDir + 'js'));
    }

    // handle app scripts
    if(jsSources.length > 0) {
        gulp.src(jsSources)
            .pipe(concat('script.js'))
            .pipe(gulpif(isProduction(), uglify()))
            .pipe(gulp.dest(outputDir + 'js'))
            .pipe(connect.reload());
    }
});

gulp.task('less', function () {
    gulp.src(lessSources)
        .pipe(less({
            paths: [ path.join(__dirname, 'components', 'less') ]
        }))
        .pipe(gulpif(isProduction(), minifyCSS({keepBreaks:true})))
        .pipe(gulp.dest(outputDir + 'css'))
        .pipe(connect.reload());
});

gulp.task('css', function() {
    // handle 3rd css
    if(cssSources3rd.length > 0) {
        gulp.src(cssSources3rd)
            .pipe(concat('lib.css'))
            .pipe(gulp.dest(outputDir + 'css'));
    }

    // handle app css
    if(cssSources.length > 0) {
        gulp.src(cssSources)
            .pipe(concat('style.css'))
            .pipe(gulpif(isProduction(), minifyCSS({keepBreaks:true})))
            .pipe(gulp.dest(outputDir + 'css'))
            .pipe(connect.reload());
    }
});

// copy the fonts
gulp.task('font', function() {
    if(fontSources.length > 0) {
        gulp.src(fontSources)
            .pipe(gulp.dest(outputDir + 'fonts'))
            .pipe(connect.reload());
    }
});

gulp.task('images', function() {
    gulp.src(imageSources)
        // TODO: may need compression in production mode
        .pipe(gulp.dest(outputDir + 'images'))
        .pipe(connect.reload())
});

gulp.task('html', function() {
    if(htmlSources.length > 0) {
        gulp.src(htmlSources)
            .pipe(gulpif(isProduction(), minifyHTML()))
            .pipe(gulp.dest(outputDir))
            .pipe(connect.reload());
    }
});

gulp.task('partials', function() {
    if(partialSources.length > 0) {
        gulp.src(partialSources)
            .pipe(gulpif(isProduction(), minifyHTML()))
            .pipe(gulp.dest(outputDir + 'partials'))
            .pipe(connect.reload());
    }
});

gulp.task('watch', function() {
    gulp.watch(jsSources, ['js']);
    gulp.watch(watchLessSources, ['less']);
    gulp.watch(cssSources, ['css']);
    gulp.watch(fontSources, ['font']);
    gulp.watch(htmlSources, ['html']);
    gulp.watch(partialSources, ['partials']);
    gulp.watch(imageSources, ['images']);
});

gulp.task('connect', function() {
    connect.server({
        root: outputDir,
        livereload: true
    });
});

gulp.task('default', ['html', 'partials', 'js', 'less', 'css', 'font', 'images', 'connect', 'watch']);

function isDevelopment() {
    return env === 'development';
}

function isProduction() {
    return env === 'production';
}