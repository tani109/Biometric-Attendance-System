clear;
clc;
close all;
global immagine n_bands h_bands n_arcs h_radius h_lato n_sectors matrice matricer num_disk


n_bands = 4;
h_bands = 20;
n_arcs = 16;
h_radius = 12;
h_lato = h_radius + ( n_bands * h_bands * 2 ) + 16;
if mod( h_lato, 2 ) == 0
h_lato = h_lato - 1;
end 
n_sectors = n_bands * n_arcs;
num_disk = 8;

matrice = zeros( h_lato );
matricer = zeros( h_lato );
for ii = 1:( h_lato * h_lato )
matrice( ii ) = whichsector( ii, 0 );
matricer( ii ) = whichsector( ii, 1 );
end 



chos = 0;
possibility = 3;

messaggio = 'Insert the number of set: each set determins a class. This set should include a number of images for each person, with some variations in expression and in the lighting.';

while chos ~= possibility, 
chos = menu( 'Fingerprint Recognition System', 'train single image', 'Train multiple images',  ...
 'Exit' );

if chos == 1
clc;
close all;
selezionato = 0;

[ namefile, pathname ] = uigetfile( { '*.bmp;*.tif;*.tiff;*.jpg;*.jpeg;*.gif;*.pgm', 'IMAGE Files (*.bmp,*.tif,*.tiff,*.jpg,*.jpeg,*.gif,*.pgm)' }, 'Chose GrayScale Image' );
if namefile ~= 0
[ img, map ] = imread( strcat( pathname, namefile ) );
selezionato = 1;
else 
disp( 'Select a grayscale image' );
end 
if ( any( namefile ~= 0 ) && length( size( img ) ) > 2 )
disp( 'Select a grayscale image' );
selezionato = 0;
end 

if selezionato == 1


immagine = double( img );

if isa( img, 'uint8' )
graylevmax = 2 ^ 8 - 1;
end 
if isa( img, 'uint16' )
graylevmax = 2 ^ 16 - 1;
end 
if isa( img, 'uint32' )
graylevmax = 2 ^ 32 - 1;
end 
fingerprint = immagine;

N = h_lato;

[ oimg, fimg, bwimg, eimg, enhimg ] = fft_enhance_cubs( fingerprint );
fingerprint = enhimg;
[ YofCenter, XofCenter ] = supercore7( fingerprint );
[ CroppedPrint ] = cropping( XofCenter, YofCenter, fingerprint );
[ NormalizedPrint, vector ] = sector_norm( CroppedPrint, 0, 0 );

for ( angle = 0:1:num_disk - 1 )
gabor = gabor2d_sub( angle, num_disk, 0 );
ComponentPrint = conv2fft( NormalizedPrint, gabor, 'same' );
[ disk, vector ] = sector_norm( ComponentPrint, 1, 0 );
finger_code1{ angle + 1 } = vector( 1:n_sectors );
end 













[ NormalizedPrint, vector ] = sector_norm( CroppedPrint, 0, 1 );

for ( angle = 0:1:num_disk - 1 )
gabor = gabor2d_sub( angle, num_disk, 1 );
ComponentPrint = conv2fft( NormalizedPrint, gabor, 'same' );
[ disk, vector ] = sector_norm( ComponentPrint, 1, 1 );
finger_code2{ angle + 1 } = vector( 1:n_sectors );
end 

if ( exist( 'fp_database.dat' ) == 2 )
load( 'fp_database.dat', '-mat' );
fp_number = fp_number + 1;
data{ fp_number, 1 } = finger_code1;
data{ fp_number, 2 } = finger_code2;
namefile_vector{ fp_number } = namefile;
path_vector{ fp_number } = pathname;
save( 'fp_database.dat', 'data', 'fp_number', 'namefile_vector', 'path_vector', '-append' );
else 
fp_number = 1;
data{ fp_number, 1 } = finger_code1;
data{ fp_number, 2 } = finger_code2;
namefile_vector{ fp_number } = namefile;
path_vector{ fp_number } = pathname;
save( 'fp_database.dat', 'data', 'fp_number', 'namefile_vector', 'path_vector' );
end 

message = strcat( 'FingerCode was succesfully added to database. Fingerprint no. ', num2str( fp_number ) );
msgbox( message, 'FingerCode DataBase', 'help' );
end 
end 



if chos == 2
clc;
close all;
selezionato = 0;

[ namefile, pathname, filterindex ] = uigetfile( { '*.bmp;*.tif;*.tiff;*.jpg;*.jpeg;*.gif;*.pgm', 'IMAGE Files (*.bmp,*.tif,*.tiff,*.jpg,*.jpeg,*.gif,*.pgm)' }, 'Chose GrayScale Image','MultiSelect','on' );
numfiles = size(namefile,2);
for ii = 1:numfiles
disp([ namefile{ii}, pathname ]);


   if namefile{ii} ~= 0
    [ img, map ] = imread( strcat( pathname, namefile{ii} ) );
    selezionato = 1;
    else 
    disp( 'Select a grayscale image' );
    end 
    if ( any( namefile{ii} ~= 0 ) && length( size( img ) ) > 2 )
    disp( 'Select a grayscale image' );
    selezionato = 0;
    end 

if selezionato == 1


immagine = double( img );

if isa( img, 'uint8' )
graylevmax = 2 ^ 8 - 1;
end 
if isa( img, 'uint16' )
graylevmax = 2 ^ 16 - 1;
end 
if isa( img, 'uint32' )
graylevmax = 2 ^ 32 - 1;
end 
fingerprint = immagine;

N = h_lato;

[ oimg, fimg, bwimg, eimg, enhimg ] = fft_enhance_cubs( fingerprint );
fingerprint = enhimg;
[ YofCenter, XofCenter ] = supercore7( fingerprint );
[ CroppedPrint ] = cropping( XofCenter, YofCenter, fingerprint );
[ NormalizedPrint, vector ] = sector_norm( CroppedPrint, 0, 0 );

for ( angle = 0:1:num_disk - 1 )
gabor = gabor2d_sub( angle, num_disk, 0 );
ComponentPrint = conv2fft( NormalizedPrint, gabor, 'same' );
[ disk, vector ] = sector_norm( ComponentPrint, 1, 0 );
finger_code1{ angle + 1 } = vector( 1:n_sectors );
end 







[ NormalizedPrint, vector ] = sector_norm( CroppedPrint, 0, 1 );

for ( angle = 0:1:num_disk - 1 )
gabor = gabor2d_sub( angle, num_disk, 1 );
ComponentPrint = conv2fft( NormalizedPrint, gabor, 'same' );
[ disk, vector ] = sector_norm( ComponentPrint, 1, 1 );
finger_code2{ angle + 1 } = vector( 1:n_sectors );
end 

if ( exist( 'fp_database.dat' ) == 2 )
load( 'fp_database.dat', '-mat' );
fp_number = fp_number + 1;
data{ fp_number, 1 } = finger_code1;
data{ fp_number, 2 } = finger_code2;
namefile_vector{ fp_number } = namefile{ii};
path_vector{ fp_number } = pathname;

save( 'fp_database.dat', 'data', 'fp_number', 'namefile_vector', 'path_vector','-append' );

else 
fp_number = 1;
data{ fp_number, 1 } = finger_code1;
data{ fp_number, 2 } = finger_code2;
namefile_vector{ fp_number } = namefile{ii};
path_vector{ fp_number } = pathname;
save( 'fp_database.dat', 'data', 'fp_number', 'namefile_vector', 'path_vector' );
end 
end
message = strcat( 'FingerCode was succesfully added to database. Fingerprint no. ', num2str( fp_number ) );
%msgbox( message, 'FingerCode DataBase', 'help' );
end 
message ='All fingerprints are added successfully';
msgbox( message );
end 




 
end 
% Decoded using De-pcode utility v1.1 from file /tmp/tmpTLQl9T.p.
% Please follow local copyright laws when handling this file.

